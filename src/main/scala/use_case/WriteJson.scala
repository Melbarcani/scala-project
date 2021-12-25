package use_case

import play.api.libs.json.{JsValue, Json, Writes}

object WriteJson {

  case class Result(limite: Point, tondeuses: Tondeuses)
  case class Tondeuses(debut: Status, instruction: Instruction, fin: Status)
  case class Status(point: Point, direction: String)
  case class Instruction(list: String*)
  case class Point(x: Int, y: Int)

  object Result{
    implicit val writes: Writes[Result] = Json.writes[Result]
  }

  object Tondeuses{
    implicit val writes: Writes[Tondeuses] = Json.writes[Tondeuses]
  }

  object Status{
    implicit val writes: Writes[Status] = Json.writes[Status]
  }

  object Instruction{
    implicit val writes: Writes[Instruction] = Json.writes[Instruction]
  }

  object Point{
    implicit val writes: Writes[Point] = Json.writes[Point]
  }

  val w: Writes[Result] = implicitly[Writes[Result]]

  def overwriteAllRobotsResults(result: Result):JsValue = w.writes(result)

}
