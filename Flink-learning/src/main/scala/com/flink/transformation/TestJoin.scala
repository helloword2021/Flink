package com.flink.transformation

import org.apache.flink.streaming.api.scala.JoinedStreams

/**
  * @author xy
  * @date ：Created in 2020/11/4 16:08
  * @desc： 测试join api
  */
// 成绩Score(唯一ID、学生姓名、学科ID、分数)
case class Score(id:Int, name:String, subjectId:Int, score:Double)
// 学科Subject(学科ID、学科名字)
case class Subject(id:Int, name:String)

object TestJoin {

  def main(args: Array[String]): Unit = {

  }

}
