package com.flink.cache

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration


/**
  * @author xy
  * @date ：Created in 2020/10/29 15:54
  * @desc：
  */
object CacheDemo {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.registerCachedFile("C:\\Users\\13297\\Desktop\\test\\1.txt","b.txt")
    val data = env.fromElements("a","b","c")
    val result = data.map(new RichMapFunction[String, String] {

      override def open(parameters: Configuration): Unit = {
          super.open(parameters)
        val myFile = getRuntimeContext.getDistributedCache.getFile("b.txt")
        val lines = FileUtils.readLines(myFile)
        val it = lines.iterator()
        while (it.hasNext){
          val line = it.next()
          println("line:"+line)
        }
      }

      override def map(value: String): String = {
        value

      }
    })

  result.print()
  }

}
