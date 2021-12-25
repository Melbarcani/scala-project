package fr.esgi.al.infrastructure.command

trait Command {
  def overwriteAllRobotsResults(lines: String): Unit

  def appendAllRobotsResults(lines: String): Unit
}
