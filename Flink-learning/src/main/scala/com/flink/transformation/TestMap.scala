package com.flink.transformation

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._

/**
  * @author xy
  * @date ：Created in 2020/11/4 13:55
  * @desc： Map
  */
case class User(id: String, name: String)

object TestMap {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data: DataStream[String] = env.fromCollection(List("1,张三", "2,你是", "3,无误"))
    val userDate: DataStream[User] = data.map(text => {
      val files: Array[String] = text.split(",")
      User(files(0), files(1))
    })
    userDate.print()
    env.execute()
  }

}
