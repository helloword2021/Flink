package com.flink.transformation

import com.flink.source.SensorReading
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
/**
  * @author xy
  * @date ：Created in 2020/11/4 15:46
  * @desc：Reduce api
  */
object TestReduce {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val streams: DataStream[SensorReading] = env.readTextFile("D:\\2.编程资料\\1.大数据\\2.专题训练\\3.Flink\\1.基础入门\\Flink-learning\\src\\main\\testdata\\sensor.txt")
      .map(data => {
        val dataArray: Array[String] = data.split(",")
        SensorReading(dataArray(0).trim, dataArray(1).trim.toLong, dataArray(2).trim.toDouble)
      }).keyBy("id")
      .reduce((x, y) => SensorReading(x.id, x.timestamp + 1, y.temperature))
    streams.print()
    env.execute()
  }

}
