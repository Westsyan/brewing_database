package test

import config.Read._
import utils.Utils

object test3 {

  def main(args: Array[String]): Unit = {
    val d= readPath("D:\\酿造\\数据库数据2\\浓香/fungus.txt").readLine

    val h = d.head.split("\t")

    d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 16).map{y=>
        (h(y),x(y))
      }.toMap
      (0,"mixiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }.foreach(println)
  }
}
