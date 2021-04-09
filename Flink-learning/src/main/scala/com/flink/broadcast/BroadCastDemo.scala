package com.flink.broadcast

import java.util

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.configuration.Configuration


object BroadCastDemo {
  def main(args: Array[String]): Unit = {
    //1.准备环境
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    //2.准备数据
    import org.apache.flink.api.scala._
    val studentDataSet: DataSet[(Int, String)] = env.fromCollection(List((1, "张三"), (2, "李四"), (3, "王五")))
    val scoreDataSet: DataSet[(Int, String, Int)] = env.fromCollection(List((1, "语文", 50), (2, "数学", 70), (3, "英文", 86)))
    //3.处理数据
    //使用studentDataSet作为广播变量将(学生ID，学科，成绩)-->(学生姓名，学科，成绩)
    val result: DataSet[(String, String, Int)] = scoreDataSet.map(new RichMapFunction[(Int, String, Int), (String, String, Int)] {
      var bc_studentMap: Map[Int, String] = null

      //3.2open方法只会执行一次,在此方法中获取广播变量
      override def open(parameters: Configuration): Unit = {
        val bc_student: util.List[(Int, String)] = getRuntimeContext.getBroadcastVariable[(Int, String)]("bc_student")
        import scala.collection.JavaConverters._
        bc_studentMap = bc_student.asScala.toMap
      }

      //3.3在map方法中使用广播变量进行数据转换
      override def map(in: (Int, String, Int)): (String, String, Int) = {
        val studentId: Int = in._1
        val studentName: String = bc_studentMap.getOrElse(studentId, "null")
        (studentName, in._2, in._3)
      }

      //3.1将studentDataSet进行广播并起个名字
    }).withBroadcastSet(studentDataSet, "bc_student")

    //4.输出数据
    result.print()

  }
}
