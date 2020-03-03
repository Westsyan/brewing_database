package controllers

import dao.AmpDao
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.Utils

import scala.concurrent.ExecutionContext

@Singleton
class AmpController@Inject()(ampDao:AmpDao, cc:ControllerComponents)
    (implicit exec:ExecutionContext) extends AbstractController(cc) {


  def to16S = Action{implicit request=>
    Ok(views.html.cn.amp.to16s())
  }

  def getAll16S = Action.async{implicit request=>
    ampDao.getAll16s.map{x=>
      val json = x.map{y=>
       val data =  Utils.jsonToMap(y.data)
        data ++ Map("16S"-> y.`16s`)
      }
      Ok(Json.toJson(json))
    }
  }

  def ITS = Action{implicit request=>
    Ok(views.html.cn.amp.its())
  }

  def getAllITS = Action.async{implicit request=>
    ampDao.getAllITS.map{x=>
      val json = x.map{y=>
        val data = Utils.jsonToMap(y.data)
        data ++ Map("ITS" -> y.its)
      }
      Ok(Json.toJson(json))
    }
  }



}
