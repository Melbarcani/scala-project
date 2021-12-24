package fr.esgi.al.funprog

import fr.esgi.al.infrastructure.command.local.WriteLocalJson
import fr.esgi.al.infrastructure.command.local.WriteLocalJson.Limits
import fr.esgi.al.infrastructure.query.local.QueryOnLocal

object Main extends App {
  println(QueryOnLocal.extractLines())
  println(WriteLocalJson.overwriteAllRobotsResults(Limits("Ab", "Bc")))
  println("Ici le programme principal")
  // Le code suivant ne compilera pas.
  // var tmp = null;
  // var tmp2 = if (tmp == 1) "yes" else 1

  // println(s"tmp: $tmp, tmp2: $tmp2")
  //cqrs
}
