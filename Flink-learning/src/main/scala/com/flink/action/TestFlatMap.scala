package com.flink.action

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * @author xy
  * @date ：Created in 2020/10/30 16:28
  * @desc：
  */
object TestFlatMap {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val data2 = env.fromCollection(List(
      "张三,中国,江西省,南昌市",
      "李四,中国,河北省,石家庄市"
    ))

    //此处可以使用flatMap
    val ss: DataSet[Product] = data2.map(t => {
      val fieldArra = t.split(",")
      List(
        (fieldArra(0), fieldArra(1)),
        (fieldArra(0), fieldArra(1), fieldArra(2)),
        (fieldArra(0), fieldArra(1), fieldArra(2), fieldArra(3))
      )
    })

    ss.print()
   

  }

}
