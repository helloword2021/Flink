package com.flink.sink

import com.flink.source.SensorReading
import org.apache.flink.streaming.api.functions.source.SourceFunction

import scala.util.Random

/**
  * @author xy
  * @date ：Created in 2020/11/4 16:43
  * @desc： 自定义source
  */
object MySensorSource {

  def main(args: Array[String]): Unit = {
    import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
    import org.apache.flink.api.scala._
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val res: DataStream[SensorReading] = env.addSource(new MySensorSource())
    res.print()
    env.execute()
  }

}

class MySensorSource extends SourceFunction[SensorReading]{

  var runing: Boolean = true
  override def run(ctx: SourceFunction.SourceContext[SensorReading]): Unit = {

    val random = new Random()
    var curTemp = 1.to(10).map(
      i => ("sen"+i,65+random.nextGaussian() * 20)
    )

    while (runing) {
      curTemp.map(
        t => (t._1,t._2 + random.nextGaussian())
      )

      val currentTime: Long = System.currentTimeMillis()

      curTemp.foreach(
        t => ctx.collect(SensorReading(t._1,currentTime,t._2))
      )

      Thread.sleep(100)

    }



  }

  override def cancel(): Unit = {

  }
}
