package controllers

import config.Read.readPath
import dao._
import javax.inject.Inject
import models.Tables.BacteriaAmpliconRow
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import utils.TableUtils.pageForm
import utils.{TableUtils, Utils}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class BacteriaAmpliconController @Inject()(bacteriaDao:BacteriaAmpliconDao,fungusDao:FungusAmpliconDao,
                                           cc: ControllerComponents)
                                          (implicit exec:ExecutionContext) extends AbstractController(cc) {




  def insertFungusJiangxiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\酱香/bacteria.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 16).map{y=>
        (h(y),x(y))
      }.toMap
      BacteriaAmpliconRow(0,"jiangxiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }

    Await.result(bacteriaDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }

  def insertFungusMixiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\米香/bacteria.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 16).map{y=>
        (h(y),x(y))
      }.toMap
      BacteriaAmpliconRow(0,"mixiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }

    Await.result(bacteriaDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }

  def insertFungusNongxiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\浓香/bacteria.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 22).map{y=>
        (h(y),x(y))
      }.toMap
      BacteriaAmpliconRow(0,"nongxiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }

    Await.result(bacteriaDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }


  def insertFungusQingxiang = Action{
    val d= readPath("D:\\酿造\\数据库数据2\\清香/bacteria.txt").readLine

    val h = d.head.split("\t")

    val row = d.tail.map(_.split("\t")).map { x =>
      val l = x.length
      val sample = (2 to 16).map{y=>
        (h(y),x(y))
      }.toMap
      BacteriaAmpliconRow(0,"qingxiang",x(1),x(l-7),x(l-6),x(l-5),x(l-4),x(l-3),x(l-2),x.last,Utils.mapToJson(sample),x.head)
    }

    Await.result(bacteriaDao.insert(row),Duration.Inf)
    Ok(Json.toJson("1"))
  }


  def bacteriaPage = Action{implicit request=>
    Ok(views.html.cn.amplicon.bacteriaPage())
  }

  def getAllBacteria(flavor:String) = Action{implicit request=>
    val page = pageForm.bindFromRequest.get
    val (x,suffix,j) = flavor match {
      case "jiangxiang" => (TableUtils.bacteriaJiangxiang,"BAJX",TableUtils.bacteriaJiangxiangSample)
      case "mixiang" => (TableUtils.bacteriaMixiang,"BAMX",TableUtils.bacteriaMixiangSample)
      case "nongxiang" => (TableUtils.bacteriaNongxiang,"BANX",TableUtils.bacteriaNongxiangSample)
      case "qingxiang" => (TableUtils.bacteriaQingxiang,"BAQX",TableUtils.bacteriaQingxiangSample)
    }

    val orderX = TableUtils.dealDataByPage(x, page,j)
    val total = orderX.size
    val tmpX = orderX.slice(page.offset, page.offset + page.limit)
    val row = tmpX.map { z =>
      val sample = Utils.jsonToMap(z.sample).toArray.map{y=>
        y._1.split('.').mkString("-") -> y._2
      }

      Map("id" -> ("<a target='_blank' href='/CN/brew/Bacteria/bacteriaInfo?id=" + z.id + "'>" + suffix + "0"*(6-z.id.toString.length) + z.id  + "</a>"),"len" -> z.len,"kingdom" -> z.kingdom,"phylum" -> z.phylum,"class" -> z.`class`,
        "order" -> z.order,"family" -> z.family,"genus" -> z.genus,"species" -> z.species ) ++ sample
    }
    Ok(Json.obj("rows" -> row, "total" -> total))
  }


  def getHeader(flavor:String) = Action{implicit request=>
    val x = flavor match {
      case "jiangxiang" => TableUtils.bacteriaJiangxiang
      case "mixiang" => TableUtils.bacteriaMixiang
      case "nongxiang" => TableUtils.bacteriaNongxiang
      case "qingxiang" => TableUtils.bacteriaQingxiang
    }
    val head = x.head
    val sample = Utils.jsonToMap(head.sample).keys.toArray.sorted.map{y=>
      y.split('.').mkString("-") -> y
    }.toMap
    val common =Json.obj("id" -> "ID","len"->"Length", "kingdom"->"Kingdom", "phylum"->"Phylum", "class"->"Class",
      "order"->"Order", "family"->"Family",
      "genus"->"Genus", "species"->"Species")

    Ok(Json.obj("common" -> common,"sample" -> Json.toJson(sample)))
  }

  def bacteriaInfo(id:Int) = Action.async{implicit request=>
    bacteriaDao.getById(id).map{x=>
      Ok(views.html.cn.amplicon.bacteriaInfo(x))
    }
  }

  def moreInfoByGeneid(geneid:String) = Action.async{implicit request=>
    geneid match {
      case x if x.startsWith("BA") =>
        bacteriaDao.getById(x.drop(4).toInt).map{y=>
          Ok(views.html.cn.amplicon.bacteriaInfo(y))
        }
      case x if x.startsWith("FU") =>
        fungusDao.getById(x.drop(4).toInt).map{y=>
          Ok(views.html.cn.amplicon.fungusInfo(y))
        }
    }
  }

  def bacteriaLinear(id: Int): Action[AnyContent] = Action.async { implicit request =>
    bacteriaDao.getById(id).map { x =>
      val sample = Utils.jsonToMap(x.sample).toArray.sortBy(_._1)
      val json = sample.map(_._2.toDouble)
      val max = sample.map(_._1)
      val finalJsons = Json.obj("infos" -> json, "maxCategory" -> max)
      Ok(Json.toJson(finalJsons))
    }
  }

  def downloadPage = Action{implicit request=>
    Ok(views.html.cn.amplicon.download())
  }

}
