package com.flink.action

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * @author xy
  * @date ：Created in 2020/10/30 17:07
  * @desc：reduceBroup
  * 可以对一个dataset或者一个group来进行聚合计算，最终聚合成一个元素
  * reduce和reduceGroup的区别
  * - reduce是将数据一个个拉取到另外一个节点，然后再执行计算
  * - reduceGroup是先在每个group所在的节点上执行计算，然后再拉取
  */
object TestReduceGroup {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val wordcountDataSet2: DataSet[(String, Int)] = env.fromCollection(List(("java" , 1) , ("java", 1) ,("scala" , 1)))
    val res1: GroupedDataSet[(String, Int)] = wordcountDataSet2.groupBy(0)
    val resultDataSet6 = res1.reduceGroup(iter =>{
      iter.reduce((wc1, wc2) => (wc1._1,wc1._2 + wc2._2))
    }
    )

    resultDataSet6.print()
  }

}
