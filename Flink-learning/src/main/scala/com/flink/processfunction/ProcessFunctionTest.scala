package com.flink.processfunction

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.util.Collector

/**
  * @author xy
  * @date ：Created in 2020/10/18 19:16
  * @desc： 需求 温度连续上升 报警
  *        测试 输入
  *        nc -lk 7777
  *        "sensor_01",112121121,36.8
  */
case class SensorReading(id: String, timestamp: Long, temperature: Double)

object ProcessFunctionTest {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val stream = env.socketTextStream("192.168.1.100", 7777)
    val dataStream = stream.map(data => {
      val dataArray = data.split(",")
      SensorReading(dataArray(0).trim, dataArray(1).trim.toLong, dataArray(2).trim.toDouble)
    })
    //    val processedStream = dataStream.keyBy(_.id).process(new TempIncreAlter())
    //    dataStream.print(" input data")
    //    processedStream.print("process data")
    //    env.execute(" process function test")

    val processStream2 = dataStream.keyBy(_.id).process(new TempChangeAlert(10.0))
    processStream2.print("process data: 大于阀值:10")
    dataStream.print(" inupt data")
    env.execute("process ")

  }

}


class TempChangeAlert(thread: Double) extends KeyedProcessFunction[String, SensorReading, (String, Double, Double)] {

  //定义一个变量存储全局变量
  lazy val lastTempState: ValueState[Double] = getRuntimeContext.getState(
    new ValueStateDescriptor[Double]("lastTemp", classOf[Double])
  )

  override def processElement(value: SensorReading, ctx: KeyedProcessFunction[String, SensorReading,
    (String, Double, Double)]#Context,
                              out: Collector[(String, Double, Double)]): Unit = {
    //获取上次的温度
    val lastTemp = lastTempState.value()
    //用当前的温度 和上次的温度 求差 ,如果大于 阀值,输出报警信息
   val diff =  (value.temperature - lastTempState.value()).abs
    if(diff > thread ) {
      out.collect(value.id,lastTemp,value.temperature)
    }
    lastTempState.update(value.temperature)
  }
}


class TempIncreAlter() extends KeyedProcessFunction[String, SensorReading, String] {
  // 定义一个状态，用来保存上一个数据的温度值
  lazy val lastTemp = getRuntimeContext.getState(new ValueStateDescriptor[Double]("lastTemp", classOf[Double]))
  //定义一个状态 用来保存定时器的时间戳
  lazy val currentTimer: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("currentTimer", classOf[Long]))

  /**
    *
    * @param value 当前处理的值
    * @param ctx   当前环境
    * @param out   输出
    */
  override def processElement(value: SensorReading,
                              ctx: KeyedProcessFunction[String,
                                SensorReading, String]#Context,
                              out: Collector[String]): Unit = {
    val preTemp = lastTemp.value()
    lastTemp.update(value.temperature)
    val curTimerTs = currentTimer.value()

    //比之前的温度高
    if (value.temperature > preTemp && curTimerTs == 0.0) {
      val timers = ctx.timerService().currentProcessingTime() + 5000L
      ctx.timerService().registerProcessingTimeTimer(timers)
      currentTimer.update(timers)
    }

    else if (value.temperature < preTemp || preTemp == 0.0) {
      ctx.timerService().deleteProcessingTimeTimer(curTimerTs)
      currentTimer.clear()
    }
  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
    out.collect(ctx.getCurrentKey + "温度连续上升")
    currentTimer.clear()
  }

}


