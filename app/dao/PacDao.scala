package dao

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import models.Tables._

import scala.concurrent.{ExecutionContext, Future}

class PacDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]   {

  import profile.api._

  def getAll : Future[Seq[PacdataRow]] = {
    db.run(Pacdata.result)
  }

  def getPacById(id:Int) : Future[PacdataRow] = {
    db.run(Pacdata.filter(_.id === id).result.head)
  }

  def getPacBySample(sample:String) : Future[PacdataRow] = {
    db.run(Pacdata.filter(_.sample === sample).result.head)
  }

  def getPacOptionBySample(sample:String) : Future[Option[PacdataRow]] = {
    db.run(Pacdata.filter(_.sample === sample).result.headOption)
  }

  def getPacBySamples(sample:Seq[String]) : Future[Seq[PacdataRow]] = {
    if(sample.isEmpty){
      db.run(Pacdata.result)
    }else{
      db.run(Pacdata.filter(_.sample.inSetBind(sample)).result)
    }
  }

}
