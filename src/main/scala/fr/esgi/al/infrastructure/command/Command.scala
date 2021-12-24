package fr.esgi.al.infrastructure.command

trait Command {
  def overwriteAllRobotsResults(lines: List[String]): Unit

  def appendAllRobotsResults(lines: List[String]): Unit
}
