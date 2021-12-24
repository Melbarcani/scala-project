package fr.esgi.al.infrastructure.command

import fr.esgi.al.infrastructure.command.local.WriteLocalJson.Limits
import play.api.libs.json.JsValue

trait Command {
  def overwriteAllRobotsResults(limits: Limits): JsValue

  def appendAllRobotsResults(lines: List[String]): Unit
}
