package controllers

import dao.{MetaDao, PacDao}
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class MetaGenomeController @Inject()(cc: ControllerComponents, metaDao: MetaDao,pacDao:PacDao)
                                    (implicit exec: ExecutionContext) extends AbstractController(cc) {


  def metaGenomePage = Action { implicit request =>
    Ok(views.html.cn.metagenome.metagenome())
  }

  def getAllMeta = Action.async { implicit request =>
    metaDao.getAll.map { x =>
      val json = x.groupBy(_.flavor).map { g =>
        val flavor = g._1 match {
          case "jiangxiang" => "酱香"
          case "nongxiang" => "浓香"
          case "mixiang" => "米香"
          case "qingxiang" => "清香"
        }
        (g._1,g._2.sortBy(_.id).map { y =>
          val code = "<a href='/CN/brew/Meta/metaInfo?sample=" + y.code +"' target='_blank'>" + y.code + "</a>"
          Json.obj("id" -> y.id, "code" -> code, "flavor" -> flavor, "source" -> y.source,
            "sampleCode" -> y.sampleCode, "textCode" -> y.textCode, "sampleInfo" -> y.sampleInfo, "time" -> y.time,
            "location" -> y.location, "dataType" -> y.dataType, "seqPlat" -> y.seqPlat,
            "seqMethod" -> y.seqMethod, "primer" -> y.primer, "seqType" -> y.seqType, "textInfo" -> y.textInfo)
        })
      }
      Ok(Json.toJson(json))
    }
  }

  def metaInfo(sample:String) = Action.async{implicit request=>
    metaDao.getBySample(sample).flatMap{x=>
      pacDao.getPacOptionBySample(sample).map{y=>
        Ok(views.html.cn.metagenome.metaInfo(x,y))
      }
    }
  }

  def getByLocation(location:String) = Action.async{implicit request=>
    metaDao.getByLocation(location).map{x=>
      val json = x.map { y =>
        val flavor = y.flavor match {
          case "jiangxiang" => "酱香"
          case "nongxiang" => "浓香"
          case "mixiang" => "米香"
          case "qingxiang" => "清香"
        }
          val code = "<a href='/CN/brew/Meta/metaInfo?sample=" + y.code +"' target='_blank'>" + y.code + "</a>"
          Json.obj("id" -> y.id, "code" -> code, "flavor" -> flavor, "source" -> y.source,
            "sampleCode" -> y.sampleCode, "textCode" -> y.textCode, "sampleInfo" -> y.sampleInfo, "time" -> y.time,
            "location" -> y.location )
        }

      Ok(Json.toJson(json))
    }


  }

}
