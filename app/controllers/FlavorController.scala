package controllers

import java.io.File

import dao.{AmpDao, FlavorDao}
import javax.inject.{Inject, Singleton}
import models.Tables.{FlavorRow, FlavordataRow}
import org.apache.commons.io.FileUtils
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import utils.Utils

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class FlavorController @Inject()(ampDao: AmpDao, flavorDao:FlavorDao, cc: ControllerComponents)
                                (implicit exec: ExecutionContext) extends AbstractController(cc) {


  def pcdata = Action { implicit request =>
    Ok(views.html.cn.flavor.pcdata())
  }

  def getAllPcdata = Action.async { implicit request =>
    ampDao.getAllPcdata.map { x =>
      val json = x.map { y =>
        Json.obj("sample" -> y.sample, "info" -> y.info, "ethanol" -> y.ethanol, "lactic" -> y.lactic,
          "sugar" -> y.sugar, "ph" -> y.ph, "acetic" -> y.acetic, "moisture" -> y.moisture, "backup1" -> y.backup1,
          "backup2" -> y.backup2)
      }
      Ok(Json.toJson(json))
    }
  }

  def macros = Action { implicit request =>
    Ok(views.html.cn.flavor.macros())
  }

  def getAllMacro = Action.async { implicit request =>
    ampDao.getAllMacro.map { x =>
      val json = x.map { y =>
        val data = Utils.jsonToMap(y.data)
        data ++ Map("name" -> y.name)
      }
      Ok(Json.toJson(json))
    }
  }

  def cas = Action { implicit request =>
    Ok(views.html.cn.flavor.cas())
  }

  def getAllCas = Action.async { implicit request =>
    ampDao.getAllCas.map { x =>
      val json = x.map { y =>
        val cas = "<a href='/CN/brew/Flavor/moreInfoByMatter?cas=" + y.cas + "' target='_blank'>" + y.cas + "</a>"
        Json.obj("cas" -> cas, "name" -> y.name, "molecular" -> y.molecular, "a" -> y.a,
          "b" -> y.b, "c" -> y.c, "d" -> y.d, "e" -> y.e, "f" -> y.f, "g" -> y.g, "h" -> y.h, "i" -> y.i, "j" -> y.j,
          "k" -> y.k, "l" -> y.l, "m" -> y.m, "n" -> y.n, "o" -> y.o, "p" -> y.p)
      }
      Ok(Json.toJson(json))
    }
  }

  def flavor = Action { implicit request =>
    Ok(views.html.cn.flavor.flavor())
  }

  def getAllFlavor = Action.async { implicit request =>
    ampDao.getAllFlavor.map { x =>
      val json = x.map { y =>
        val p = Utils.jsonToMap(y.pcdata)
        val f = Utils.jsonToMap(y.flavor)
        val w = Utils.jsonToMap(y.wine)
        Json.obj("cas" -> y.cas, "types" -> y.types, "chinese" -> y.chinese, "english" -> y.english, "分子式" -> p("分子式"),
          "分子量" -> p("分子量"), "熔点" -> p("熔点"), "沸点" -> p("沸点"), "LogP" -> p("LogP"), "Mass" -> p("Mass"),
          "Dimension" -> p("Dimension"), "香气描述" -> f("香气描述"), "阈值" -> f("阈值"), "工具书上阈值" -> f("工具书上阈值"),
          "含量" -> f("含量"), "前处理方式" -> f("前处理方式"), "func" -> y.func, "酱香" -> w("酱香"), "清香" -> w("清香"),
          "米香" -> w("米香"), "兼香" -> w("兼香"), "老白干" -> w("老白干"), "特香" -> w("特香"), "芝麻香" -> w("芝麻香"),
          "凤香" -> w("凤香"), "小曲清香" -> w("小曲清香"), "豉香" -> w("豉香"), "药香" -> w("药香"))
      }
      Ok(Json.toJson(json))
    }
  }

  def moreInfoByMatter(cas: String) = Action.async { implicit request =>

    ampDao.getFlavorByCas(cas).map { x =>
      var row : FlavorRow = FlavorRow(0,"","","","","","","","")
      var p :  Map[String,String] = Map()
      var f :  Map[String,String] = Map()
      var w :  Map[String,String] = Map()
      if (x.length == 1) {
         row = x.head
         p = Utils.jsonToMap(row.pcdata)
         f = Utils.jsonToMap(row.flavor)
         w = Utils.jsonToMap(row.wine)
      }
      Ok(views.html.cn.flavor.MoreInfoByMatter(row, p, f, w))
    }

  }


  def insertJiangxiang = Action{
    val f =FileUtils.readLines(new File("D:\\酿造\\data-2020-1-6/jiangxiang.txt"),"GB2312").asScala
    val h = f.head.split("\t")

    val row = f.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (8 to 22).map{y=>
        (h(y),x(y))
      }.toMap
      FlavordataRow(0,x.head,"jiangxiang",x(1),x(2),x(3),x(4),x(5),x(6),x(7),Utils.mapToJson(sample))
    }

    Await.result(flavorDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }

  def insertQingxiang = Action{
    val f =FileUtils.readLines(new File("D:\\酿造\\data-2020-1-6/qingxiang.txt"),"GB2312").asScala

    val h = f.head.split("\t")

    val row = f.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (8 to 22).map{y=>
        (h(y),x(y))
      }.toMap
      FlavordataRow(0,x.head,"qingxiang",x(1),x(2),x(3),x(4),x(5),x(6),x(7),Utils.mapToJson(sample))
    }
    Await.result(flavorDao.insert(row),Duration.Inf)

    Ok(Json.toJson("1"))
  }

  def isNum(x :String) = {
    try {
      "<sub>" + x.toInt + "</sub>"
    }catch {
      case e:Exception => x
    }
  }

  def getData(flavor:String) = Action.async{
    flavorDao.getByFlavor(flavor).map{x=>
      val json = x.map{y=>
        val ppm = Utils.jsonToMap(y.ppm)
        val cas =  "<a href='/CN/brew/Flavor/flavorInfo?id=" + y.id + "' target='_blank'>" + y.cas + "</a>"
        val formula = y.formula.map(f=> isNum(f.toString)).mkString
        Map("id" ->  y.id.toString ,"cas" -> cas,"flavor" -> y.flavor,"english" -> y.english,
          "chinese" -> y.chinese,"formula" -> formula,"weight"->y.weight,"struction"->y.struction,
        "boiling" -> y.boiling,"melting"->y.melting) ++ ppm
      }
      val sample = Utils.jsonToMap(x.head.ppm).keys.toArray.sorted.map{y=>
        y.split('.').mkString("-") -> y
      }.toMap

      val header =  Json.obj("cas" -> "CAS","english" -> "英文名",
        "chinese" -> "中文名","formula"->"分子式","weight"->"分子量","struction"->"结构式",
      "boiling"->"沸点","melting"->"熔点")

      Ok(Json.obj("head" -> header,"sample" -> sample,"datas" -> json))
    }
  }

  def flavorInfo(id:Int) = Action.async{implicit request=>
    flavorDao.getById(id).map{x=>
      Ok(views.html.cn.flavor.flavorPage(x))
    }
  }

  def flavorCloumn(id: Int): Action[AnyContent] = Action.async { implicit request =>
    flavorDao.getById(id).map { x =>
      val sample = Utils.jsonToMap(x.ppm).toArray.sortBy(_._1)
      val json = sample.map(_._2.toDouble)
      val max = sample.map(_._1)
      val finalJsons = Json.obj("infos" -> json, "maxCategory" -> max)
      Ok(Json.toJson(finalJsons))
    }
  }

}
