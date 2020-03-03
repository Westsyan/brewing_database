package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import config.Read._
import play.api.libs.json.Json
import utils.Utils

@Singleton
class MapController@Inject()(cc:ControllerComponents)
                            (implicit exec:ExecutionContext) extends AbstractController(cc)   {




  def mapPage = Action{implicit request=>
    Ok(views.html.cn.map.map())
  }

  def getChinaJson  = Action{implicit request=>
    val china = readPath(Utils.path + "/china.json").readAll
    Ok(Json.toJson(china))
  }

}
