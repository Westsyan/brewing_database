package utils

import java.lang.reflect.Field

import models.Tables._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.JsValue

import scala.collection.mutable

case class PageData(limit: Int, offset: Int, order: String, search: Option[String], sort: Option[String])

object TableUtils {





  var transJiangxiang : Seq[TranscriptomeRow] = Seq()
  var transJiangxiangSample : Map[Int,Map[String,String]]= Map()

  var fungusJiangxiang : Seq[FungusAmpliconRow] = Seq()
  var fungusJiangxiangSample : Map[Int,Map[String,String]]= Map()
  var fungusMixiang : Seq[FungusAmpliconRow] = Seq()
  var fungusMixiangSample : Map[Int,Map[String,String]]= Map()
  var fungusQingxiang : Seq[FungusAmpliconRow] = Seq()
  var fungusQingxiangSample : Map[Int,Map[String,String]]= Map()
  var fungusNongxiang : Seq[FungusAmpliconRow] = Seq()
  var fungusNongxiangSample : Map[Int,Map[String,String]]= Map()


  var bacteriaJiangxiang : Seq[BacteriaAmpliconRow] = Seq()
  var bacteriaJiangxiangSample : Map[Int,Map[String,String]]= Map()
  var bacteriaMixiang : Seq[BacteriaAmpliconRow] = Seq()
  var bacteriaMixiangSample : Map[Int,Map[String,String]]= Map()
  var bacteriaQingxiang : Seq[BacteriaAmpliconRow] = Seq()
  var bacteriaQingxiangSample : Map[Int,Map[String,String]]= Map()
  var bacteriaNongxiang : Seq[BacteriaAmpliconRow] = Seq()
  var bacteriaNongxiangSample : Map[Int,Map[String,String]]= Map()


  var geneidToId : Map[String,Int] = Map()


  var tableMap : mutable.HashMap[String,Seq[JsValue]] = mutable.HashMap()

  var tableTimeMap : mutable.HashMap[String,Long] = mutable.HashMap()


  val pageForm = Form(
    mapping(
      "limit" -> number,
      "offset" -> number,
      "order" -> text,
      "search" -> optional(text),
      "sort" -> optional(text)
    )(PageData.apply)(PageData.unapply)
  )

  def dealDataByPage[T](x: Seq[T], page: PageData,json:Map[Int,Map[String,String]]): Seq[T] = {
    val searchX = x.filter { y =>
      page.search match {
        case None => true
        case Some(text) =>
          val array = text.split("\\s+").map(_.toUpperCase).toBuffer
          val texts = y.getClass.getDeclaredFields.toBuffer.map { x: Field =>
            x.setAccessible(true)
            x.get(y).toString
          }.init
          array.forall { z =>
            texts.map(_.toUpperCase.indexOf(z) != -1).reduce(_ || _)
          }
      }
    }

    val sortX = page.sort match {
      case None => searchX
      case Some(y) =>
        val b = searchX.take(1000).forall { tmpData =>
          try {
            val method = tmpData.getClass.getDeclaredMethod(y)
            val value = method.invoke(tmpData).toString
            Utils.isDouble(value)
          }catch {
            case e:Exception => true
          }
        }
        if (b) {
          searchX.sortBy { z =>
            try{
              val method = z.getClass.getDeclaredMethod(y)
              method.invoke(z).toString.toDouble
            }catch {
              case e:Exception =>
                val method = z.getClass.getDeclaredMethod("id")
                val map = json(method.invoke(z).toString.toInt)
                map(y).toDouble
            }
          }
        } else {
          searchX.sortBy { z =>
            val method = z.getClass.getDeclaredMethod(y)
            method.invoke(z).toString
          }
        }
    }

    val orderX = page.order match {
      case "asc" => sortX
      case "desc" => sortX.reverse
    }

    orderX

  }

}


