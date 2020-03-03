package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class FlavorDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]   {

  import profile.api._

  def insert(row:Seq[FlavordataRow]) : Future[Unit] = {
    db.run(Flavordata ++= row).map(_=>())
  }

  def getByFlavor(flavor:String) : Future[Seq[FlavordataRow]] = {
    db.run(Flavordata.filter(_.flavor === flavor).result)
  }

  def getById(id:Int) : Future[FlavordataRow] ={
    db.run(Flavordata.filter(_.id === id).result.head)
  }
}
