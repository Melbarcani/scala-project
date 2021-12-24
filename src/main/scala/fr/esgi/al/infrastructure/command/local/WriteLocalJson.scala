package fr.esgi.al.infrastructure.command.local

import better.files.File
import fr.esgi.al.infrastructure.command.Command
import fr.esgi.al.infrastructure.utils.FilesUtils
import play.api.libs.json.{JsValue, Json, Writes}

object WriteLocalJson extends Command{

 /* case class results(list: List[String])

  object results{
    val createFile:
  }*/
  /*val w: Writes[List[String]] = implicitly[Writes[List[String]]]

  implicit val writes: Writes[List[String]] = Json.writes[List[String]]*/

  case class Limits(x: String, y: String)

  object Limits{
    implicit val writes: Writes[Limits] = Json.writes[Limits]
  }

  val w: Writes[Limits] = implicitly[Writes[Limits]]

  def overwriteAllRobotsResults(limits: Limits):JsValue = w.writes(limits)

  val f: File = FilesUtils.getJsonOutput

  f.createIfNotExists()
    .appendLine()
    .appendLines(overwriteAllRobotsResults(Limits("ab","bc")).toString())

  override def appendAllRobotsResults(lines: List[String]): Unit = ???
}
