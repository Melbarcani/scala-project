package fr.esgi.al.funprog

import fr.esgi.al.infrastructure.command.local.WriteLocalJson
import fr.esgi.al.infrastructure.query.local.QueryOnLocal
import use_case.WriteJson
import use_case.WriteJson.{Instruction, Point, Result, Status, Tondeuses}

object Main extends App {
  println(QueryOnLocal.extractLines())
  val p =Point(1,2)
  val ins = Instruction("A", "B")
  val stat = Status(p,"a")
  val t = Tondeuses(stat, ins, stat)
  val s = WriteJson.overwriteAllRobotsResults(Result(p, t)).toString()
  println(WriteLocalJson.appendAllRobotsResults(s))
  //cqrs
}
