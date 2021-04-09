package com.flink.transformation

/**
  * @author xy
  * @date ：Created in 2020/11/4 15:41
  * @desc： filter api
  */
object TestFilter {

  def main(args: Array[String]): Unit = {
    import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
    import org.apache.flink.api.scala._
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val wordData: DataStream[String] = env.fromCollection(List("hadoop","hive"))
    // filter 会自动遍历每一个元素
    val res: DataStream[String] = wordData.filter(_.length > 4)
    res.print()
    env.execute()
  }



}
