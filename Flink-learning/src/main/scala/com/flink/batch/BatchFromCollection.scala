package com.flink.batch

import org.apache.flink.api.scala.ExecutionEnvironment

/**
  * @author xy
  * @date ：Created in 2020/10/30 16:12
  * @desc： ExecutionEnvironment 类的学习
  */
object BatchFromCollection {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    import org.apache.flink.api.scala._

//    val ds = env.fromElements("spark","flink")
//    val ds1 = env.fromElements((1,"sss"))
//    ds1.print()
//    ds.print()

    env.fromCollection(List("spark",""))


  }

}
