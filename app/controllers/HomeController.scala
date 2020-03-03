package controllers

import javax.inject._
import play.api.libs.json.Json
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(ws: WSClient, cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.cn.home.index())
  }

  def index2 = Action{
    Ok(views.html.us.home.index())
  }

  def index3 = Action{
    Ok(views.html.us.home.index2())
  }

  def test = Action.async{
    val data = Map(
      "act"->"GetHeCenData",
      "casId"->"141-78-6",
      "start" -> "0",
      "end" -> "3"
    )
    ws.url("https://www.chemsrc.com/SearchSup")
      .post(data).map{x=>

      println(x.json)
      Ok(Json.toJson(1))
    }


  }

}
