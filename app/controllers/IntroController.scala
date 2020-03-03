package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class IntroController@Inject()(cc:ControllerComponents)
                              (implicit exec:ExecutionContext) extends AbstractController(cc)  {

  def intro = Action{implicit request=>
    Ok(views.html.cn.intro.introduce())
  }

  def directorPage = Action{implicit request=>
    Ok(views.html.cn.intro.director())
  }
}
