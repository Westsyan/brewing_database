package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class MetaDao  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{

  import profile.api._


  def getAll : Future[Seq[MetagenomeRow]] = {
    db.run(Metagenome.result)
  }

  def getById(id:Int) : Future[MetagenomeRow] = {
    db.run(Metagenome.filter(_.id === id).result.head)
  }

  def getBySample(sample:String) : Future[MetagenomeRow] = {
    db.run(Metagenome.filter(_.code === sample).result.head)
  }

  def getByLocation(location:String) : Future[Seq[MetagenomeRow]] = {
    db.run(Metagenome.filter(_.location === location).result)
  }

}
