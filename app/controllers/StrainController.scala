package controllers

import dao.{AmpDao, StrainDao}
import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class StrainController @Inject()(ampDao:AmpDao, strainDao:StrainDao, cc:ControllerComponents)
                                (implicit exec:ExecutionContext) extends AbstractController(cc) {


  def strainPage = Action{implicit request=>
    Ok(views.html.cn.strain.strainPage())
  }


  def getAllStrain = Action.async{implicit request=>
    strainDao.getAll.map{x=>
      val json = x.map{y=>
        val chinese = "<a href='/CN/brew/Strain/srainInfoById?id=" + y.id + "' target='_blank'>" +
          y.chinese + "</a>"
        Json.obj("id"->y.id,"initial"->y.initial,"speciesType"->y.speciesType,"chinese"->chinese,
        "latin" -> y.latin,"genus"->y.genus,"species"->y.species,"lamCode"->y.lamCode,"patent"->y.patent,
        "patentCode"->y.patentCode,"source"->y.source,"apply"->y.apply,"feature"->y.feature,"text"->y.text,
          "notes"->y.notes)
      }
      Ok(Json.toJson(json))
    }
  }

  def srainInfoById(id:Int) = Action.async{implicit request=>
    strainDao.getById(id).map{x=>
      Ok(views.html.cn.strain.strainMoreinfo(x))
    }
  }


  def strainInfo = Action{implicit request=>
    Ok(views.html.cn.strain.straininfo())
  }

  def getAllStrainInfo = Action.async{implicit request=>
    ampDao.getAllStrainInfo.map{x=>
      val json = x.map{y=>
        Json.obj("name" -> y.name,"header" -> y.header,"save_place" -> y.savePlace,"save_type" -> y.saveType,
        "originid" -> y.originid,"chinese" -> y.chinese,"medium" -> y.medium,"position" -> y.position,
          "source" -> y.source, "apply" -> y.apply,"save_people" -> y.savePeople,"teacher" -> y.teacher,
          "save_date" -> y.saveDate, "primer_seq" -> y.primerSeq, "seq_index" -> y.seqIndex,
          "emf" -> y.emf,"emf_name" -> y.emfName,"train" -> y.train,"train_name"->y.trainName,"strain_form"->y.strainForm)
      }
      Ok(Json.toJson(json))
    }
  }



  def strainPhe = Action{implicit request=>
    Ok(views.html.cn.strain.strainphe())
  }

  def getAllStrainPhe = Action.async{implicit request=>
    ampDao.getAllStrainphe.map{x=>
      val json = x.map{y=>
        Json.obj("name"->y.name,"code" -> y.code,"chinese" -> y.chinese,"latin" -> y.latin,
          "separation" -> y.separation, "sugar" -> y.sugar,"protein" -> y.protein,"tole" -> y.tole)
      }
      Ok(Json.toJson(json))
    }
  }

  case class searchData(chinese:Option[String],latin:Option[String])

  val searchForm = Form(
    mapping(
      "chinese" -> optional(text),
      "latin" -> optional(text)
    )(searchData.apply)(searchData.unapply)
  )

  def searchStrain = Action{implicit request=>
    val data = searchForm.bindFromRequest.get
    if(data.chinese.isEmpty && data.latin.isEmpty){
      Ok(Json.obj("valid" -> "false"))
    }else{
      val c = data.chinese.getOrElse("none")
      val l = data.latin.getOrElse("none")
      val row = Await.result(strainDao.getByName(c,l),Duration.Inf)
      val json = row.map{y=>
        val chinese = "<a href='/CN/brew/Strain/srainInfoById?id=" + y.id + "' target='_blank'>" +
          y.chinese + "</a>"
        Json.obj("id"->y.id,"initial"->y.initial,"speciesType"->y.speciesType,"chinese"->chinese,
          "latin" -> y.latin,"genus"->y.genus,"species"->y.species,"lamCode"->y.lamCode,"patent"->y.patent,
          "patentCode"->y.patentCode,"source"->y.source,"apply"->y.apply,"feature"->y.feature,"text"->y.text,
          "notes"->y.notes)
      }
      Ok(Json.obj("result" -> json,"valid" -> "true"))
    }
  }

}
