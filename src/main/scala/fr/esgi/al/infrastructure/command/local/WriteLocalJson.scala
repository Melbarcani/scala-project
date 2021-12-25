package fr.esgi.al.infrastructure.command.local

import better.files.File
import fr.esgi.al.infrastructure.command.Command
import fr.esgi.al.infrastructure.utils.FilesUtils

import scala.annotation.tailrec

object WriteLocalJson extends Command{


  val f: File = FilesUtils.getJsonOutput

  /*f.createIfNotExists()
    .appendLine()
    .appendLines(overwriteAllRobotsResults(Limits("ab","bc")).toString())*/



  def appendAllRobotsResults(lines: List[String]): File = {
    f.createIfNotExists()
    appendAllRobotsResultsHelper(f, lines)
  }

   @tailrec
   private def appendAllRobotsResultsHelper(file: File, lines: List[String]):File = lines match {
    case Nil => file.appendLine()
    case s::r =>
      f.appendLine(s)
      appendAllRobotsResultsHelper(f,r)
  }

  override def overwriteAllRobotsResults(lines: String): Unit = ???

  override def appendAllRobotsResults(lines: String): Unit = {
    f.createIfNotExists().appendLine(lines)
    print("a")
  }
}
