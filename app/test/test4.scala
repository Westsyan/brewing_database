package test

import java.io.FileOutputStream
import java.net.URL


object test4 {


  def main(args: Array[String]): Unit = {

  val url = new URL("https://webbook.nist.gov/cgi/cbook.cgi?Struct=C123079&Type=Color")
    val uc = url.openConnection()
    val input = uc.getInputStream
    val out = new FileOutputStream("D:\\酿造\\数据库数据2\\酱香\\image/a.png")

    var j = 0
    while((j = input.read()) != -1){
      out.write(j)
    }
    input.close()


  }
}
