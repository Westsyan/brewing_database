package dao

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import models.Tables._

import scala.concurrent.{ExecutionContext, Future}

class AmpDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  def getAll16s : Future[Seq[`16sRow`]] = {
    db.run(`16s`.result)
  }

  def getAllITS : Future[Seq[ItsRow]] = {
    db.run(Its.result)
  }

  def getAllCas : Future[Seq[CasRow]] = {
    db.run(Cas.result)
  }

  def getAllFlavor : Future[Seq[FlavorRow]] = {
    db.run(Flavor.result)
  }

  def getFlavorByCas(cas:String) : Future[Seq[FlavorRow]] = {
    db.run(Flavor.filter(_.cas === cas).result)
  }

  def getAllMacro : Future[Seq[MacroRow]] = {
    db.run(Macro.result)
  }

  def getAllPcdata : Future[Seq[PcdataRow]] = {
    db.run(Pcdata.result)
  }

  def getAllStrainInfo : Future[Seq[StraininfoRow]] = {
    db.run(Straininfo.result)
  }

  def getAllStrainphe : Future[Seq[StrainpheRow]] = {
    db.run(Strainphe.result)
  }

}
