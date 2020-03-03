package controllers

import java.io.File
import java.nio.file.Files

import config.Blast._
import javax.inject.Inject
import org.apache.commons.io.FileUtils
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import utils.{ExecCommand, TableUtils, Utils}

import scala.concurrent.ExecutionContext
import config.Read._

import collection.JavaConverters._

class BlastController @Inject()(cc: ControllerComponents)
                               (implicit exec: ExecutionContext) extends AbstractController(cc) {


  def blastBefore = Action { implicit request =>
    Ok(views.html.cn.blast.blast())
  }

  case class blastData(blastType: String, method: String, queryText: String, db: Seq[String], evalue: String, wordSize: String, maxTargetSeqs: String)

  val blastForm = Form(
    mapping(
      "blastType" -> text,
      "method" -> text,
      "queryText" -> text,
      "db" -> seq(text),
      "evalue" -> text,
      "wordSize" -> text,
      "maxTargetSeqs" -> text
    )(blastData.apply)(blastData.unapply)
  )

  def blastRun = Action(parse.multipartFormData) { implicit request =>
    val data = blastForm.bindFromRequest.get
    val tmpDir = Files.createTempDirectory("tmpDir").toString
    val seqFile = new File(tmpDir, "seq.fa")
    val d = data.method match {
      case "text" =>
        if(!data.queryText.contains(">")){">Query_1\n" + data.queryText}else{data.queryText}
      case "file" =>
        val file = request.body.file("file").get
        val datas = FileUtils.readFileToString(file.ref.file)
        if(!datas.contains(">")){">Query_1\n" + datas}else datas
    }
    FileUtils.writeStringToFile(seqFile, d)


    val outXml = new File(tmpDir, "out.xml")
    val outHtml = new File(tmpDir, "out.html")
    val outTable = new File(tmpDir, "table.xls")
    val execCommand = new ExecCommand

    val blastType = data.blastType match {
      case "blastn" => "blastn"
      case "blastnGenome" => "blastn"
      case "blastp" => "blastp"
      case "blastx" => "blastx"
    }

    val blastPath = Utils.path + "/blastData/"
    val db = blastPath + "brew"

    val seq = data.db.flatMap { x =>
      readPath(blastPath + x).readLine
    }
    val seqList = tmpDir + "/seqList.txt"
    FileUtils.writeLines(new File(seqList), seq.asJava)

    val blast2Html = data.blastType match {
      case "blastx" => "blastx2html"
      case _ => "blast2html"
    }

    val command1 = Utils.path + "/tools/ncbi-blast-2.9.0+/bin/%s%s -query ".format(blastType, Utils.suffix) +
      seqFile.getAbsolutePath + " -db " + db + " -seqidlist " + seqList +
      " -outfmt 5 -evalue " + data.evalue + " -max_target_seqs " + data.maxTargetSeqs +
      " -word_size " + data.wordSize + " -out " + outXml.getAbsolutePath

    val jinja = blastType
    val command2 = "python " + Utils.path + s"/tools/blast2html/$blast2Html.py -i " + outXml.getAbsolutePath + " -o " +
      outHtml.getAbsolutePath +
      " --template %s/tools/blast2html/%s.jinja".format(Utils.path, jinja)
    val command3 = "perl " + Utils.path + "/tools/Blast2table -format 10 -xml " + outXml.getAbsolutePath + " -header "


    execCommand.exect(Array(command1, command2, command3), tmpDir)
    if (execCommand.isSuccess) {

      val excel = execCommand.getOutStr
      FileUtils.writeStringToFile(outTable, excel)
      Ok(Json.obj("html" -> tmpDir.replaceAll("\\\\", "/"), "excel" -> excel, "types" -> data.blastType))
    } else {
      Utils.deleteDirectory(tmpDir)
      Ok(Json.obj("valid" -> "false", "message" -> execCommand.getErrStr))
    }
  }


  def blastResultBefore(path: String, types: String): Action[AnyContent] = Action {
    implicit request =>
      Ok(views.html.cn.blast.blastResult(path, types))
  }

  def blastResult(path: String, types: String): Action[AnyContent] = Action {
    implicit request =>
      val html = Utils.readFileToString(new File(path + "/out.html"))
      val block = path.getSyntenyData
      val json = Json.obj("html" -> (html + "\n" + Utils.scriptHtml), "block" -> block)
      Utils.deleteDirectory(path)
      Ok(json)
  }


  def getSeq = Action {

    val path = "F:\\database\\brewing_db\\blastData/"
    /*
        val bajx = TableUtils.bacteriaJiangxiang.map { x =>
          ">BAJX" + "0" * (6 - x.id.toString.length) + x.id + "\n" + x.seq
        }.mkString("\n")
        FileUtils.writeStringToFile(new File(path, "bajx.fasta"), bajx)

        val banx = TableUtils.bacteriaNongxiang.map { x =>
          ">BANX" + "0" * (6 - x.id.toString.length) + x.id + "\n" + x.seq
        }.mkString("\n")
        FileUtils.writeStringToFile(new File(path, "banx.fasta"), banx)

        val bamx = TableUtils.bacteriaMixiang.map { x =>
          ">BAMX" + "0" * (6 - x.id.toString.length) + x.id + "\n" + x.seq
        }.mkString("\n")
        FileUtils.writeStringToFile(new File(path, "bamx.fasta"), bamx)

        val baqx = TableUtils.bacteriaQingxiang.map { x =>
          ">BAQX" + "0" * (6 - x.id.toString.length) + x.id + "\n" + x.seq
        }.mkString("\n")
        FileUtils.writeStringToFile(new File(path, "baqx.fasta"), baqx)

        val fuqx = TableUtils.fungusQingxiang.map { x =>
          ">FUQX" + "0" * (6 - x.id.toString.length) + x.id + "\n" + x.seq
        }.mkString("\n")
        FileUtils.writeStringToFile(new File(path, "fuqx.fasta"), fuqx)

        val fumx = TableUtils.fungusMixiang.map { x =>
          ">FUMX" + "0" * (6 - x.id.toString.length) + x.id + "\n" + x.seq
        }.mkString("\n")
        FileUtils.writeStringToFile(new File(path, "fumx.fasta"), fumx)

        val funx = TableUtils.fungusNongxiang.map { x =>
          ">FUNX" + "0" * (6 - x.id.toString.length) + x.id + "\n" + x.seq
        }.mkString("\n")
        FileUtils.writeStringToFile(new File(path, "funx.fasta"), funx)

    */

    val ba = TableUtils.bacteriaJiangxiang.map(x => "BAJX" + "0" * (6 - x.id.toString.length) + x.id) ++
      TableUtils.bacteriaMixiang.map(x => "BAMX" + "0" * (6 - x.id.toString.length) + x.id) ++
      TableUtils.bacteriaNongxiang.map(x => "BANX" + "0" * (6 - x.id.toString.length) + x.id) ++
      TableUtils.bacteriaQingxiang.map(x => "BAQX" + "0" * (6 - x.id.toString.length) + x.id)

    FileUtils.writeLines(new File(path, "ba.txt"), ba.toBuffer.asJava)

    val fu = TableUtils.fungusJiangxiang.map(x => "FUJX" + "0" * (6 - x.id.toString.length) + x.id) ++
      TableUtils.fungusMixiang.map(x => "FUMX" + "0" * (6 - x.id.toString.length) + x.id) ++
      TableUtils.fungusNongxiang.map(x => "FUNX" + "0" * (6 - x.id.toString.length) + x.id) ++
      TableUtils.fungusQingxiang.map(x => "FUQX" + "0" * (6 - x.id.toString.length) + x.id)

    FileUtils.writeLines(new File(path, "fu.txt"), fu.toBuffer.asJava)

    Ok(Json.toJson("1"))

  }

}
