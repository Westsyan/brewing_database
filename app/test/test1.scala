package test

import java.io.File

import org.apache.commons.io.FileUtils
import play.api.libs.json.Json
import utils.Utils

import scala.collection.JavaConverters._

object test1 {


  def main(args: Array[String]): Unit = {

    getFlavor
  }


  def getMacro = {
    val f = FileUtils.readLines(new File("D:\\酿造\\数据库构建模板数据/macro.txt"), "UTF-8").asScala

    f.map{x=>
      val array = Array("A05","A10","A15","A20","A25","A32","B05","B10","B15","B20","B25","B32","C05","C10","C15",
        "C20","C25","C32","MTF15C","MTF20A","MTF25A","MTF30A","MTF30B","MTF30C")
      val l = x.split("\t")
      val data =Utils.mapToJson(array.zipWithIndex.map{y=>
        y._1 -> l(y._2+1)
      }.toMap)
      "0\t" + l.head + "\t" + data
    }.foreach(println)
  }


  def getIts = {
    val f = FileUtils.readLines(new File("D:\\酿造\\数据库构建模板数据/its.txt"), "UTF-8").asScala

    f.map{x=>
      val array = Array("A05","A06","A07","A08","A09","A10","A11","A12","A13","A14","A15","A16","A17","A18","A19","A20",
        "A21","A22")
      val l = x.split("\t")
      val data =Utils.mapToJson(array.zipWithIndex.map{y=>
        y._1 -> l(y._2+1)
      }.toMap)
      "0\t" + l.head + "\t" + data
    }.foreach(println)
  }

  def get16s = {
    val f = FileUtils.readLines(new File("D:\\酿造\\数据库构建模板数据/16s.txt"), "UTF-8").asScala

    f.map{x=>
      val array = Array("A05","A10","A15","A20","A25","A32","B05","B10","B15","B20","B25","B32","C05","C10","C15",
        "C20","C25","C32")
      val l = x.split("\t")
      val data =Utils.mapToJson(array.zipWithIndex.map{y=>
        y._1 -> l(y._2+1)
      }.toMap)
      "0\t" + l.head + "\t" + data
    }.foreach(println)

  }

  def getFlavor = {
    val f = FileUtils.readLines(new File("D:\\酿造\\数据库构建模板数据/新建文本文档.txt"), "UTF-8").asScala

    f.drop(2).map { x =>
      val l = (x+" ").split("\t")
      val molecularArray = Array("分子式","分子量", "熔点", "沸点", "LogP", "Mass", "Dimension", "LRI")
      val molecular = Json.toJson(
        molecularArray.zipWithIndex.map { y =>
          y._1 -> l(y._2 + 4)
        }.toMap
      )

      val flavorArray = Array("香气描述", "阈值", "工具书上阈值", "含量", "前处理方式")
      val flavor = Json.toJson(
        flavorArray.zipWithIndex.map { y =>
          y._1 -> l(y._2 + 12)
        }.toMap
      )

      val wineArray = Array("酱香", "清香", "浓香", "米香", "兼香", "老白干", "特香", "芝麻香", "凤香", "小曲清香", "芝兼浓",
        "豉香", "药香")
      val wine = Json.toJson(
        wineArray.zipWithIndex.map { y =>
          y._1 -> l(y._2 + 18)
        }.toMap
      )
      val le ="0"*(11 - l.head.length) + l.head

      Array(0,le, l(1), l(2), l(3),molecular,flavor,l(17),wine).mkString("\t")
    }.foreach(println)

  }
}
