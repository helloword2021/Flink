package com.flink.stream

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
/**
  * @author xy
  * @date ：Created in 2020/11/3 17:09
  * @desc： wordcount nc -lk 7777  给 7777端口发送数据
  *       统计 5s输入的单词
  *
  */
object WindowWordCount {

  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val text: DataStream[String] = env.socketTextStream("192.168.1.100",9999)

    val counts: DataStream[(String, Int)] = text.flatMap(_.toLowerCase.split(",").filter(_.nonEmpty))
      .map((_, 1)).keyBy(_._1).timeWindow(Time.seconds(5)).sum(1)
    counts.print()
    env.execute("sss")

  }

}
