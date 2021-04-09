package com.flink.function

import org.apache.flink.api.common.functions.{FilterFunction, RichFilterFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
/**
  * @author xy
  * @date ：Created in 2020/10/13 22:10
  * @desc： 过滤函数
  */
object FilterFilter {


  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val datas = new Array[String](2)
    datas(1) = "abc"
    datas(2) = "flink"
//
//    val test = datas.filter(new RichFilterFunction[String] {
//      override def filter(t: String): Boolean = {
//        t.contains("flink")
//      }
//    })





  }




}
