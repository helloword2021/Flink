package com.flink.action

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
/**
  * @author xy
  * @date ：Created in 2020/11/3 16:53
  * @desc： 测试map
  */
object TestMap {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val dataStream: DataStream[String] = env.readTextFile("D:\\2.编程资料\\1.大数据\\2.专题训练\\3.Flink\\1.基础入门\\Flink-learning\\src\\main\\scala\\com\\flink\\action\\test.txt")
    val dataStr: DataStream[String] = dataStream.map(
      //读取的是每一行数据
      x => x + "a"
    )
    dataStr.writeAsText("D:\\2.编程资料\\1.大数据\\2.专题训练\\3.Flink\\1.基础入门\\Flink-learning\\src\\main\\scala\\com\\flink\\action\\test1.txt")
    env.execute("ss")
  }

}
