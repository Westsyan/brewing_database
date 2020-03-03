package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class StrainDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]   {

  import profile.api._

  def getAll : Future[Seq[StrainRow]] = {
    db.run(Strain.result)
  }

  def getById(id:Int) : Future[StrainRow] ={
    db.run(Strain.filter(_.id === id).result.head)
  }

  def getByName(c:String,l:String) : Future[Seq[StrainRow]] = {
    db.run(Strain.filter(x=> x.chinese === c || x.latin === l).result)
  }

}
