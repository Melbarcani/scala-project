package fr.esgi.al.infrastructure.command.local

import fr.esgi.al.domaine.Model.{Result, Status, TondeuseResult}
import fr.esgi.al.infrastructure.utils.Instructions.Instruction
import play.api.libs.json.{JsValue, Json, Writes}

import scala.annotation.tailrec

object CreateJson {

  case class ResultJson(limite: PointJson, tondeuses: List[TondeusesJson])

  case class TondeusesJson(
      debut: StatusJson,
      instructions: List[String],
      fin: StatusJson
  )

  case class StatusJson(point: PointJson, direction: String)

  case class InstructionJson(value: String)

  case class DirectionJson(value: String)

  case class PointJson(x: Int, y: Int)

  object ResultJson {
    implicit val writes: Writes[ResultJson] = Json.writes[ResultJson]
  }

  object TondeusesJson {
    implicit val writes: Writes[TondeusesJson] = Json.writes[TondeusesJson]
  }

  object StatusJson {
    implicit val writes: Writes[StatusJson] = Json.writes[StatusJson]
  }

  object PointJson {
    implicit val writes: Writes[PointJson] = Json.writes[PointJson]
  }

  val w: Writes[ResultJson] = implicitly[Writes[ResultJson]]

  def execute(result: Result): JsValue = w.writes(convertToJsonResult(result))

  def convertToJsonResult(result: Result): ResultJson =
    ResultJson(
      PointJson(result.size.x, result.size.y),
      convertTondeusesToJsonResult(result.tondeuseResult, Nil)
    )

  @tailrec
  def convertTondeusesToJsonResult(
      tondeuseResult: List[TondeuseResult],
      tondeuseInJson: List[TondeusesJson]
  ): List[TondeusesJson] = tondeuseResult match {
    case tondeuse :: rest =>
      convertTondeusesToJsonResult(
        rest,
        tondeuseInJson.appended(
          TondeusesJson(
            convertToStatusJson(tondeuse.startStatus),
            tondeuse.instructions.map(i => i.value),
            convertToStatusJson(tondeuse.endStatus)
          )
        )
      )
    case Nil => tondeuseInJson
  }

  def convertToStatusJson(status: Status): StatusJson =
    StatusJson(
      PointJson(status.coordinates.x, status.coordinates.y),
      status.direction.value
    )

  def getInstructionsString(instructions: List[Instruction]): List[String] =
    instructions.map(i => i.value)
}
