package com.flink.transformation
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
/**
  * @author xy
  * @date ：Created in 2020/11/4 16:01
  * @desc：按照内置的方式来进行聚合
  */
object TestAggr {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val res: DataStream[(String, Int)] = env.fromCollection(List(("java" , 1) , ("java", 1) ,("scala" , 1)))




  }

}
