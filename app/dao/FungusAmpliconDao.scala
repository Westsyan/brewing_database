package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class FungusAmpliconDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]    {

  import profile.api._

  def insert(row: Seq[FungusAmpliconRow]) : Future[Unit] = {
    db.run(FungusAmplicon ++= row).map(_=>())
  }

  def getAll : Future[Seq[FungusAmpliconRow]] = {
    db.run(FungusAmplicon.result)
  }

  def getById(id:Int) : Future[FungusAmpliconRow] = {
    db.run(FungusAmplicon.filter(_.id === id).result.head)
  }

}
