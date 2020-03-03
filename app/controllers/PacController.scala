package controllers

import dao.PacDao
import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class PacController @Inject()(pacDao: PacDao, cc: ControllerComponents)
                             (implicit exec: ExecutionContext) extends AbstractController(cc) {


  def pacPage = Action { implicit request =>
    Ok(views.html.cn.pac.pacPage())
  }

  def getAllPcadata = Action.async { implicit request =>
    pacDao.getAll.map { x =>
      val json = x.map { y =>
        Json.obj("id" -> y.id,
          "sample" -> ("<a href='/CN/brew/Meta/metaInfo?sample=" + y.sample + "' target='_blank'>" + y.sample + "</a>"), "acidity" -> y.acidity, "ph" -> y.ph, "sugar" -> y.sugar,
          "moisture" -> y.moisture, "alcohol" -> y.alcohol, "aceticAcid" -> y.aceticAcid, "lacticAcid" -> y.lacticAcid,
          "temp" -> y.temp)
      }
      Ok(Json.toJson(json))
    }
  }

  def pacInfo(id: Int) = Action.async { implicit request =>
    pacDao.getPacById(id).map { x =>
      Ok(views.html.cn.pac.pacInfo(x))
    }
  }

  def pacInfoBySample(sample: String) = Action.async { implicit request =>
    pacDao.getPacBySample(sample).map { x =>
      Ok(views.html.cn.pac.pacInfo(x))
    }
  }

  def getColumn(head: String) = {

  }

  def pacColumnPage(head: String) = Action { implicit request =>
    val name = head match {
      case "acidity" => "酸度"
      case "ph" => "PH"
      case "sugar" => "还原糖"
      case "moisture" => "水分"
      case "alcohol" => "乙醇"
      case "aceticAcid" => "乙酸"
      case "lacticAcid" => "乳酸"
      case "temp" => "温度"
    }
    Ok(views.html.cn.pac.pacColumnPage(name, head))
  }

  def getPacColumnData(head: String): Action[AnyContent] = Action.async { implicit request =>
    pacDao.getAll.map { x =>
      val json = head match {
        case "acidity" => x.map(_.acidity.toDouble)
        case "ph" => x.map(_.ph.toDouble)
        case "sugar" => x.map(_.sugar.toDouble)
        case "moisture" => x.map(_.moisture.toDouble)
        case "alcohol" => x.map(_.alcohol.toDouble)
        case "aceticAcid" => x.map(_.aceticAcid.toDouble)
        case "lacticAcid" => x.map(_.lacticAcid.toDouble)
        case "temp" => x.map(_.temp.toDouble)
      }

      val max = x.map(_.sample)
      val finalJsons = Json.obj("infos" -> json, "maxCategory" -> max)
      Ok(Json.toJson(finalJsons))
    }
  }


  def radar = Action {
    Ok(views.html.cn.pac.radar())
  }

  def getAllSample = Action.async{
    pacDao.getAll.map{ x =>
      Ok(Json.obj("sample" -> x.map(_.sample)))
    }
  }

  case class radarData(sample:Seq[String])

  val radarForm = Form(
    mapping(
      "sample" -> seq(text)
    )(radarData.apply)(radarData.unapply)
  )

  def getRadarData = Action.async {implicit request=>
    val radar = radarForm.bindFromRequest.get
    pacDao.getPacBySamples(radar.sample).map { x =>
      val indicator = Array(
        Json.obj("name" -> "酸度", "max" -> x.map(_.acidity.toDouble).max * 1.2),
        Json.obj("name" -> "PH", "max" -> x.map(_.ph.toDouble).max * 1.2),
        Json.obj("name" -> "还原糖", "max" -> x.map(_.sugar.toDouble).max * 1.2),
        Json.obj("name" -> "水分", "max" -> x.map(_.moisture.toDouble).max * 1.2),
        Json.obj("name" -> "乙醇", "max" -> x.map(_.alcohol.toDouble).max * 1.2),
        Json.obj("name" -> "乙酸", "max" -> x.map(_.aceticAcid.toDouble).max * 1.2),
        Json.obj("name" -> "乳酸", "max" -> x.map(_.lacticAcid.toDouble).max * 1.2),
        Json.obj("name" -> "温度", "max" -> x.map(_.temp.toDouble).max * 1.2)
      )
      val data = x.map { y =>
        val value = Array(y.acidity, y.ph, y.sugar, y.moisture, y.alcohol, y.aceticAcid, y.lacticAcid, y.temp)
        Json.obj("value" -> value, "name" -> y.sample)
      }
      val legend = x.map(_.sample)
      Ok(Json.obj("indicator" -> indicator, "data" -> data, "legend" -> legend))

    }


  }


}
