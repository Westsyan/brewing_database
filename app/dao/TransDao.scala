package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class TransDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]  {

  import profile.api._

  def insert(row:Seq[TranscriptomeRow]) : Future[Unit] = {
    db.run(Transcriptome ++= row).map(_=>())
  }

  def getAll : Future[Seq[TranscriptomeRow]] = {
    db.run(Transcriptome.result)
  }

  def getById(id:Int) : Future[TranscriptomeRow] = {
    db.run(Transcriptome.filter(_.id === id).result.head)
  }

}
