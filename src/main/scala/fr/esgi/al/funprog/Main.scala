package fr.esgi.al.funprog

import fr.esgi.al.infrastructure.command.local.WriteLocalJson
import fr.esgi.al.infrastructure.query.local.QueryOnLocal
import fr.esgi.al.use_case.WriteJson
import fr.esgi.al.use_case.WriteJson.{PointJson, ResultJson, StatusJson, TondeusesJson}

object Main extends App {
  /*val d = QueryOnLocal.parseData()*/
  val d = QueryOnLocal.extractData()
  println(d)
  val p = PointJson(1, 2)
  /*val ins = InstructionJson(List("A", "B"))*/
  val stat = StatusJson(p, "a")
  val t = TondeusesJson(stat, List("A", "B"), stat)
  val s = WriteJson.overwriteAllRobotsResults(ResultJson(p, List(t))).toString()
  println(WriteLocalJson.appendAllRobotsResults(s))
  //cqrs
}
