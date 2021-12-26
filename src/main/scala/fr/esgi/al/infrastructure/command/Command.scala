package fr.esgi.al.infrastructure.command

trait Command {
  def overwriteAllRobotsResults(lines: String): Unit

  // Strategy

  def appendAllRobotsResults(lines: String): Unit
}
