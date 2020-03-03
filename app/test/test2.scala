package test

import config.Read._
import utils.Utils

object test2 {


  def main(args: Array[String]): Unit = {

    val f =  readPath("D:\\酿造\\数据库数据2\\酱香/trans.txt").readLine

    val head = f.head.split("\t")

    val sample = head.zipWithIndex.drop(10)
    f.map{x=>
      val l = x.split("\t")
      val json = sample.map{y=>
        if(y._2 < 17){
          y._1 -> l(y._2)
        }else{
          y._1 -> "-"
        }
      }.toMap

      (0,l.head,"jiangxiang",l(1),l(2),l(3),l(4),l(5),l(6),l(7),l(8),l(9),Utils.mapToJson(json))
    }.foreach(println)

  }

}
