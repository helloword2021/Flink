package com.flink.source

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext

import scala.util.Random

/**
  * @author xy
  * @date ：Created in 2020/10/13 11:13
  * @desc：
  */

case class SensorReading(id: String,timestamp: Long,temperature: Double)

 class CustomSource extends SourceFunction[SensorReading] {

   def main(args: Array[String]): Unit = {
     var cusSource = new CustomSource()

   }

   var running: Boolean = true

   override def run(ctx: SourceFunction.SourceContext[SensorReading]): Unit = {
     var random  = new Random()
     var curTmp = 1.to(10).map(
       i => ("sensor_" + i, 65 + random.nextGaussian() * 20)
     )

     while (running) {
       curTmp = curTmp.map(
         t => (t._1,t._2 + random.nextGaussian())
       )

       val curTime = System.currentTimeMillis()

      curTmp.foreach(
        t => ctx.collect(SensorReading(t._1,curTime,t._2))
      )

       Thread.sleep(100)

     }

   }

   override def cancel(): Unit = {
      running = false
   }
 }
