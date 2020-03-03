package test

import java.io.File

import cn.edu.hfut.dmic.webcollector.model.{CrawlDatums, Page}
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler
import org.apache.commons.io.FileUtils

import scala.collection.JavaConverters._
import scala.collection.mutable

object Crawler {

  var exce = mutable.Buffer[String]()
  var text =  mutable.Buffer[String]()

  class Crawler(crawlPath: String, autoParse: Boolean) extends BreadthCrawler(crawlPath, autoParse) {
    override def visit(page: Page, next: CrawlDatums): Unit = {
      try {
        val url = page.url()

       //https://webbook.nist.gov/网站数据
        val list = page.select("main").select("li").toString.split("\n")

        val id = page.select("main").select("h1").text()
        val form = getText(list.find(_.contains("Formula")).get)
        val mole = getText(list.find(_.contains("Molecular weight")).get)
        val cas =   getText(list.find(_.contains("CAS Registry Number")).get)
        text += Array(cas,id,form,mole).mkString("\t")

      } catch {
        case e: Exception =>
          exce += page.url()
      }
    }
  }


  def getText(html:String) = {
    val start = html.lastIndexOf("</strong>")+10
    val end = html.lastIndexOf("</li>")
    html.slice(start,end)
  }

  def main(args: Array[String]): Unit = {

    val code = new Crawler("crawle", false)



    val names = FileUtils.readLines(new File("D:\\酿造\\数据库数据2\\清香/enName.txt"),"GB2312").asScala

    println(names.length)
    names.foreach{x=>
      code.addSeed("https://webbook.nist.gov/cgi/cbook.cgi?Name=" +
        x.trim.replaceAll(",","%2C").replaceAll(" ","+") +"&Units=SI")
      println("https://webbook.nist.gov/cgi/cbook.cgi?Name=" +  x.trim +"&Units=SI")
    }



    code.start(1)

    FileUtils.writeLines(new File("D:\\酿造\\数据库数据2\\清香/cra.txt"),text.asJava)
    FileUtils.writeLines(new File("D:\\酿造\\数据库数据2\\清香/error.txt"),exce.asJava)


  }

}
