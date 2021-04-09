package com.flink.batch

import org.apache.flink.api.scala.{AggregateDataSet, DataSet, ExecutionEnvironment}
import org.apache.flink.streaming.api.scala._
/**
  * @author xy
  * @date ：Created in 2020/10/12 17:21
  * @desc： wordcount
  */
object WordCount {

  def main(args: Array[String]): Unit = {
      //创建运行环境
    val env = ExecutionEnvironment.getExecutionEnvironment
    val inputDS: DataSet[String] = env.readTextFile("D:\\2.编程资料\\1.大数据\\2.专题训练\\3.Flink\\1.基础入门\\Flink-learning\\src\\main\\testdata\\test.txt")
    val wordCountDS: AggregateDataSet[(String, Int)] = inputDS.flatMap(_.split(" ")).map((_, 1)).groupBy(0)
      .sum(1)




    wordCountDS.print()

  }

}
