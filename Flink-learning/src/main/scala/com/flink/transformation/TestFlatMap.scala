package com.flink.transformation

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._

import scala.collection.mutable.ArrayOps
/**
  * @author xy
  * @date ：Created in 2020/11/4 15:23
  * @desc： FlatMap api
  */
object TestFlatMap {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val data2: DataStream[String] = env.fromCollection(List(
      "张三,中国,江西省,南昌市", "李四,中国,河北省,石家庄市"
    ))
    val resultData: DataStream[((String, String), (String, String, String))] = data2.flatMap(text => {
      val fieldArr: ArrayOps.ofRef[String] = text.split(",")
      List(
        (
          (fieldArr(0), fieldArr(1)),
          (fieldArr(0), fieldArr(1), fieldArr(2))
        )
      )
    })
    resultData.print()
    env.execute()

  }



}
