package controllers

import config.Read.readPath
import dao.FungusAmpliconDao
import javax.inject.Inject
import models.Tables.FungusAmpliconRow
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import utils.TableUtils.pageForm
import utils.{TableUtils, Utils}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class FungusAmpliconController @Inject()(fungusDao:FungusAmpliconDao, cc: ControllerComponents)
                                        (implicit exec:ExecutionContext) extends AbstractController(cc) {


  def insertFungusJiangxiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\酱香/fungus.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 16).map{y=>
        (h(y),x(y))
      }.toMap
      FungusAmpliconRow(0,"jiangxiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }

    Await.result(fungusDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }

  def insertFungusMixiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\米香/fungus.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 16).map{y=>
        (h(y),x(y))
      }.toMap
      FungusAmpliconRow(0,"mixiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }

    Await.result(fungusDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }

  def insertFungusNongxiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\浓香/fungus.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 22).map{y=>
        (h(y),x(y))
      }.toMap
      FungusAmpliconRow(0,"nongxiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }

    Await.result(fungusDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }

  def insertFungusQingxiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\清香/fungus.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 16).map{y=>
        (h(y),x(y))
      }.toMap
      val kingdom = if(x(l-7).startsWith("k__")) x(l-7).drop(3) else x(l-7)
      val phylum = if(x(l-6).startsWith("p__")) x(l-6).drop(3) else x(l-6)
      val classes = if(x(l-5).startsWith("c__")) x(l-5).drop(3) else x(l-5)
      val order = if(x(l-4).startsWith("o__")) x(l-4).drop(3) else x(l-4)
      val family = if(x(l-3).startsWith("f__")) x(l-3).drop(3) else x(l-3)
      val genus = if(x(l-2).startsWith("g__")) x(l-2).drop(3) else x(l-2)
      val species = if(x(l-1).startsWith("s__")) x(l-1).drop(3) else x(l-1)


      FungusAmpliconRow(0,"qingxiang",x(1),kingdom,phylum,classes,order,family,genus,species,Utils.mapToJson(sample),x.head)
    }

    Await.result(fungusDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }

  def fungusPage = Action{implicit request=>
    Ok(views.html.cn.amplicon.fungusPage())
  }


  def getAllFungus(flavor:String) = Action{implicit request=>
    val page = pageForm.bindFromRequest.get
    val (x,suffix,s) = flavor match {
      case "jiangxiang" => (TableUtils.fungusJiangxiang,"FUJX",TableUtils.fungusJiangxiangSample)
      case "mixiang" => (TableUtils.fungusMixiang,"FUMX",TableUtils.fungusMixiangSample)
      case "nongxiang" => (TableUtils.fungusNongxiang,"FUNX",TableUtils.fungusNongxiangSample)
      case "qingxiang" => (TableUtils.fungusQingxiang,"FUQX",TableUtils.fungusQingxiangSample)
    }

    val orderX = TableUtils.dealDataByPage(x, page,s)
    val total = orderX.size
    val tmpX = orderX.slice(page.offset, page.offset + page.limit)
    val row = tmpX.map { z =>
      val sample = Utils.jsonToMap(z.sample).toArray.map{y=>
        y._1.split('.').mkString("-") -> y._2
      }

      Map("id" -> ("<a target='_blank' href='/CN/brew/Fungus/fungusInfo?id=" + z.id + "'>" + suffix +
        "0"*(6-z.id.toString.length) +   z.id + "</a>"),"len" -> z.len,"kingdom" -> z.kingdom,"phylum" -> z.phylum,"class" -> z.`class`,
        "order" -> z.order,"family" -> z.family,"genus" -> z.genus,"species" -> z.species ) ++ sample
    }
    Ok(Json.obj("rows" -> row, "total" -> total))
  }

  def getHeader(flavor:String) = Action{implicit request=>
    val x = flavor match {
      case "jiangxiang" => TableUtils.fungusJiangxiang
      case "mixiang" => TableUtils.fungusMixiang
      case "nongxiang" => TableUtils.fungusNongxiang
      case "qingxiang" => TableUtils.fungusQingxiang
    }
    val head = x.head
    val sample = Utils.jsonToMap(head.sample).keys.toArray.sorted.map{y=>
      y.split('.').mkString("-") -> y
    }.toMap

    val common =  Json.obj("id" -> "ID","len"->"Length", "kingdom"->"Kingdom", "phylum"->"Phylum", "class"->"Class",
      "order"->"Order", "family"->"Family",
      "genus"->"Genus", "species"->"Species")

    Ok(Json.obj("common" -> common,"sample" -> Json.toJson(sample)))
  }


  def fungusInfo(id:Int) = Action.async{implicit request=>
    fungusDao.getById(id).map{x=>
      Ok(views.html.cn.amplicon.fungusInfo(x))
    }
  }

  def fungusLinear(id: Int): Action[AnyContent] = Action.async { implicit request =>
    fungusDao.getById(id).map { x =>
      val sample = Utils.jsonToMap(x.sample).toArray.sortBy(_._1)
      val json = sample.map(_._2.toDouble)
      val max = sample.map(_._1)
      val finalJsons = Json.obj("infos" -> json, "maxCategory" -> max)
      Ok(Json.toJson(finalJsons))
    }
  }

}
