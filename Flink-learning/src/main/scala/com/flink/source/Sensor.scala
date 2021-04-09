package com.flink.source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._


//case class SensorReading(id: String,timestamp: Long,temperature: Double)
/**
  * @author xy
  * @date ：Created in 2020/10/13 10:51
  * @desc： 定义source ,从list集合中读取
  */
object Sensor {

  def main(args: Array[String]): Unit = {

      val env = StreamExecutionEnvironment.getExecutionEnvironment.setBufferTimeout(1)
    val stream1 = env.fromCollection(List(
      SensorReading("sensor_01", 112121121, 36.8),
      SensorReading("sensor_02", 112121112, 37.8),
      SensorReading("sensor_03", 112121123, 38.8)

    ))
    //
    stream1.print("stream: ").setParallelism(1)
    env.execute()
  }

}
