package com.flink.udf


import org.apache.flink.api.common.functions.{FilterFunction, RichFilterFunction, RichMapFunction}
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.streaming.api.scala._

/**
  * @author xy
  * @date ：Created in 2020/11/5 11:16
  * @desc：自定义UDF函数  自定义filter函数
  */
object FilterFilter {

  def main(args: Array[String]): Unit = {
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val dataSet: DataSet[String] = env.fromElements("a","b","c")
  //  val filterData: DataSet[String] = dataSet.filter(new MyFilter)
    //匿名函数
    val filterData: DataSet[String] = dataSet.filter(new RichFilterFunction[String] {
      override def filter(value: String): Boolean = {
        value.contains("a")
      }
    })
    filterData.print()

  }

}


class MyFilter extends FilterFunction[String]{
  override def filter(value: String): Boolean = {
    value.contains("a");
  }

}
