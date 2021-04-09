package com.flink.action
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * @author xy
  * @date ：Created in 2020/10/30 16:48
  * @desc：测试Reduce  数据集返回的都是DataSet
  */
object TestReduce {

  def main(args: Array[String]): Unit = {
    //reduce
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val res: DataSet[(String, Int)] = env.fromCollection(List(("java" , 1) , ("java", 1) ,("java" , 1)))
    val res3: DataSet[(String, Int)] = res.reduce((wc1, wc2) => (wc1._1, wc1._2 + wc2._2))
    res3.print()


    //groupby
    val wordcountDataSet3: DataSet[(String, Int)] = env.fromCollection(List(("java" , 1) , ("java", 1) ,("scala" , 1)))
    val res5: AggregateDataSet[(String, Int)] = wordcountDataSet3.groupBy(0).sum(1)
    res5.print()


  }

}
