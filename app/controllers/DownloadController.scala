package controllers

import java.io.File
import java.nio.file.Files

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents, RangeResult}
import utils.Utils.windowsPath
import utils.{ExecCommand, Utils}

import scala.concurrent.ExecutionContext

@Singleton
class DownloadController @Inject()(cc: ControllerComponents)(implicit exec: ExecutionContext) extends AbstractController(cc) {





  def downloadAmp(file: String) = Action { implicit request =>
    println(file)
    val filename = "\"" + file + "\""
    Ok.sendFile(new File(Utils.path + "/download/" + file)).withHeaders(
      //缓存
      CACHE_CONTROL -> "max-age=3600",
      CONTENT_DISPOSITION -> ("attachment; filename=" + filename),
      CONTENT_TYPE -> "application/x-download"
    )
  }

  //断点续传
  def linkIgvData(path: String) = Action { implicit request =>
    RangeResult.ofFile(new File(Utils.path + "/igvData/" + path), request.headers.get(RANGE), Some("application/octet-stream"))
  }

  def download(file: String) = Action { implicit request =>
    val name = file.split(" ").mkString("_")
    val filename = "\"" + name + "\""
    Ok.sendFile(new File(Utils.path + "/download/" + name)).withHeaders(
      //缓存
      CACHE_CONTROL -> "max-age=3600",
      CONTENT_DISPOSITION -> ("attachment; filename=" + filename),
      CONTENT_TYPE -> "application/x-download"
    )
  }


  def downloadBlastByRange(name: String, range: String, blastType: String) = Action {
    val fasta = "brew.fasta"


    val execCommand = new ExecCommand
    range.split("Range").tail.foreach { x =>
      val r = x.trim.split(":").last.split("to").map(_.trim).sortBy(_.toInt)
      val ra = name + ":" + r.mkString("-")
      val command = if (new File(windowsPath).exists()) {
        Utils.path + "/tools/samtools-0.1.19/samtools.exe faidx " + Utils.path + "/blastData/" + fasta + " " + ra
      } else {
        "samtools faidx " + Utils.path + "/blastData/" + fasta + " " + ra
      }
      execCommand.exec(command)
    }

    val seq = execCommand.getOutStr
    Ok(seq).withHeaders(
      //缓存
      CACHE_CONTROL -> "max-age=3600",
      CONTENT_DISPOSITION -> ("attachment; filename=" + name + ".fasta"),
      CONTENT_TYPE -> "application/x-download"
    )

  }

  def downloadBlastByName(name: String, blastType: String) = Action {

    val fasta = "brew.fasta"

    val execCommand = new ExecCommand
    val command = if (new File(windowsPath).exists()) {
      Utils.path + "/tools/samtools-0.1.19/samtools.exe faidx " + Utils.path + "/blastData/" + fasta + " " + name
    } else {
      "samtools faidx " + Utils.path + "/blastData/" + fasta + " " + name
    }

    val tmpDir = Files.createTempDirectory("tmpDir").toString
    val seqFile = new File(tmpDir + "/seq.fasta")
    execCommand.execo(command, seqFile)
    println(execCommand.getErrStr)
    Ok.sendFile(seqFile).withHeaders(
      //缓存
      CACHE_CONTROL -> "max-age=3600",
      CONTENT_DISPOSITION -> ("attachment; filename=" + name + ".fasta"),
      CONTENT_TYPE -> "application/x-download"
    )

  }



  def downloadSelect(ids:String,types:String) = Action { implicit request =>
    val csv =  ids.split(",").map { id =>
      Utils.readFileToString(Utils.path + "/download/" + id + "." + types).trim
    }.mkString("\n")
    Ok(csv).withHeaders(
      //缓存
      CACHE_CONTROL -> "max-age=3600",
      CONTENT_DISPOSITION -> ("attachment; filename=select." + types),
      CONTENT_TYPE -> "application/x-download"
    )

  }

}
