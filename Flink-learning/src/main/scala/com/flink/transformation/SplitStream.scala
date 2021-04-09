package com.flink.transformation

import org.apache.flink.streaming.api.scala.{DataStream, SplitStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._

/**
  * @author xy
  * @date ：Created in 2020/11/4 17:49
  * @desc： DataStream 分成 splitStream
  */
case class SensorReadings(id: String, timestamp: Long, temperature: Double)

object SplitStream {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val streams: DataStream[SensorReadings] = env.readTextFile("D:\\2.编程资料\\1.大数据\\2.专题训练\\3.Flink\\1.基础入门\\Flink-learning\\src\\main\\testdata\\sensor.txt")
      .map(data => {
        val dataArray: Array[String] = data.split(",")
        SensorReadings(dataArray(0).trim, dataArray(1).trim.toLong, dataArray(2).trim.toDouble)
      })
    val spiltStream: SplitStream[SensorReadings] = streams.split(sensorData => {
      if (sensorData.temperature > 36) Seq("high") else Seq("low")
    })

    val high: DataStream[SensorReadings] = spiltStream.select("high")
    val low: DataStream[SensorReadings] = spiltStream.select("low")

    high.print()
    env.execute()
  }

}
