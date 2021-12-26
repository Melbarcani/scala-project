package fr.esgi.al.use_case

import play.api.libs.json.{JsValue, Json, Writes}

object WriteJson {

  case class ResultJson(limite: PointJson, tondeuses: List[TondeusesJson])
  case class TondeusesJson(debut: StatusJson, instructions: List[String], fin: StatusJson)
  case class StatusJson(point: PointJson, direction: String)
  case class PointJson(x: Int, y: Int)

  object ResultJson{
    implicit val writes: Writes[ResultJson] = Json.writes[ResultJson]
  }

  object TondeusesJson{
    implicit val writes: Writes[TondeusesJson] = Json.writes[TondeusesJson]
  }

  object StatusJson{
    implicit val writes: Writes[StatusJson] = Json.writes[StatusJson]
  }

  object PointJson{
    implicit val writes: Writes[PointJson] = Json.writes[PointJson]
  }

  val w: Writes[ResultJson] = implicitly[Writes[ResultJson]]

  def overwriteAllRobotsResults(result: ResultJson):JsValue = w.writes(result)

}
