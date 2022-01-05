package fr.esgi.al.funprog

import fr.esgi.al.infrastructure.query.local.QueryOnLocal

object Main extends App {
  val d = QueryOnLocal.extractAndStartMove()
  println("Files created in : " + d.toString)
}
