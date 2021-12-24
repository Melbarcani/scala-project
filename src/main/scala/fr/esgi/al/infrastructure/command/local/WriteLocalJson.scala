package fr.esgi.al.infrastructure.command.local

import fr.esgi.al.infrastructure.command.Command
import play.api.libs.json.Json

object WriteLocalJson extends Command{

  val Writes
  override def overwriteAllRobotsResults(lines: List[String]): Unit = Json.writes[List[String]]

  override def appendAllRobotsResults(lines: List[String]): Unit = ???
}
