package com.flink.udf

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.api.common.functions.{FilterFunction, RichFilterFunction, RichMapFunction}
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * @author xy
  * @date ：Created in 2020/11/5 11:31
  * @desc：
  */
object TestMyFlatMap {

  def main(args: Array[String]): Unit = {
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val dataSet: DataSet[Int] = env.fromElements(1, 2, 3)
    val res: DataSet[(Int, Int)] = dataSet.flatMap(new MyFlatMap)
    res.print()
  }

}

class MyFlatMap extends RichFlatMapFunction[Int, (Int, Int)] {

  var sub = 0

  override def open(parameters: Configuration): Unit = {
    print("open do some init work" + "\n")
  }

  override def close(): Unit = {
    print("close ")
  }

  override def flatMap(value: Int, out: Collector[(Int, Int)]): Unit = {
    if (value % 2 == sub) {
      out.collect((value, value))
    }
  }
}
