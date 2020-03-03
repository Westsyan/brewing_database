package controllers

import config.Read.readPath
import dao.TransDao
import javax.inject.Inject
import models.Tables.TranscriptomeRow
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import utils.TableUtils.pageForm
import utils.{TableUtils, Utils}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class TransController@Inject()(transDao: TransDao,
                               cc:ControllerComponents)
                              (implicit exec:ExecutionContext) extends AbstractController(cc){


  def insertTrans = Action{implicit request=>

    val f =  readPath("D:\\酿造\\数据库数据2\\酱香/trans.txt").readLine

    val head = f.head.split("\t")

    val sample = head.zipWithIndex.drop(10)
    val row = f.tail.map{x=>
      val l = x.split("\t")
      val json = sample.map{y=>
        if(y._2 < 17){
          y._1 -> l(y._2)
        }else{
          y._1 -> "0"
        }
      }.toMap

      TranscriptomeRow(0,l.head,"jiangxiang",l(1),l(2),l(3),l(4),l(5),l(6),l(7),l(8),l(9),Utils.mapToJson(json))
    }


    Await.result(transDao.insert(row),Duration.Inf)

    Ok(Json.toJson("1"))
  }

  def transPageBefore = Action{implicit request=>
    Ok(views.html.cn.trans.transPage())
  }

  def getAllTrans = Action{implicit request=>
    val page = pageForm.bindFromRequest.get

    val x = TableUtils.transJiangxiang
    val j = TableUtils.transJiangxiangSample

    val orderX = TableUtils.dealDataByPage(x, page,j)
    val total = orderX.size
    val tmpX = orderX.slice(page.offset, page.offset + page.limit)

    val row = tmpX.map{y=>
      val trans = Utils.jsonToMap(y.trans).toArray.map{z=>
        z._1.split('.').mkString("-") -> z._2
      }
       Map("gene" ->("<a target='_blank' href='/CN/brew/Trans/transMoreinfo?id=" + y.id + "'>" + y.gene + "</a>"),
         "nog" -> y.nog,"function" -> y.function,"category" -> y.category,
        "ko" -> y.ko,"pathway"->y.pathway,"enzyme"->y.enzyme,"modules" -> y.modules,"nr" -> y.nr,
         "taxonomy" -> y.taxonomy) ++ trans
    }
    Ok(Json.obj("rows" -> row, "total" -> total))


  }

  def getHeader(flavor:String) = Action{implicit request=>
    val x = TableUtils.transJiangxiang
    val head = x.head
    val sample = Utils.jsonToMap(head.trans).keys.toArray.sorted.map{y=>
      y.split('.').mkString("-") -> y
    }.toMap
    val common = Json.obj("gene" ->"Gene", "nog" ->  "NOG","function" -> "Function",
      "category" -> "Category",
      "ko" -> "KO","pathway"->"Pathway","enzyme"->"Enzyme","modules" -> "Modules","nr" -> "Nr_annotation")

    Ok(Json.obj("common" -> common,"sample" ->  Json.toJson(sample)))
  }

  def transMoreinfo(id:Int) = Action.async{implicit request=>
    transDao.getById(id).map{x=>
      Ok(views.html.cn.trans.transMoreinfo(x))
    }
  }

  def transLinear(id: Int): Action[AnyContent] = Action.async { implicit request =>
    transDao.getById(id).map { x =>
      val sample = Utils.jsonToMap(x.trans).toArray.sortBy(_._1)
      val json = sample.map(_._2 match {
        case "-" => 0
        case i => i.toDouble
      })
      val max = sample.map(_._1)
      val finalJsons = Json.obj("infos" -> json, "maxCategory" -> max)
      Ok(Json.toJson(finalJsons))
    }
  }

}
