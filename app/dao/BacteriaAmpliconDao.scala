package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class BacteriaAmpliconDao  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]   {

  import profile.api._

  def insert(row: Seq[BacteriaAmpliconRow]) : Future[Unit] = {
    db.run(BacteriaAmplicon ++= row).map(_=>())
  }

  def getAll : Future[Seq[BacteriaAmpliconRow]] = {
    db.run(BacteriaAmplicon.result)
  }

  def getById(id:Int) : Future[BacteriaAmpliconRow] = {
    db.run(BacteriaAmplicon.filter(_.id === id).result.head)
  }


}
