package com.flink.source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
/**
  * @author xy
  * @date ：Created in 2020/10/13 11:47
  * @desc：
  */
case class SensorReading1(id: String,timestamp: Long,temperature: Double)
object SensorDemo {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val stream = env.readTextFile("D:\\2.编程资料\\1.大数据\\2.专题训练\\3.Flink\\1.基础入门\\Flink-learning\\src\\main\\testdata\\sensor" +
      ".txt")
      .map(data => {
        val dataArray = data.split(",")
        SensorReading1(dataArray(0).trim, dataArray(1).trim.toLong, dataArray(2).trim.toDouble)
      }).keyBy("id")
      .reduce((x, y) => SensorReading1(x.id, x.timestamp + 1, y.temperature))
    stream.print()
    env.execute()
  }

}
