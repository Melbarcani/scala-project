package fr.esgi.al.use_case

import fr.esgi.al.domaine.Model.{Point, Starter, Status, Tondeuse}
import fr.esgi.al.infrastructure.query.local.QueryOnLocal
import fr.esgi.al.infrastructure.utils.Directions.Direction
import fr.esgi.al.infrastructure.utils.Instructions.Instruction
import fr.esgi.al.use_case.Board.MoveRobots
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

class BoardSpecs extends AnyFunSuite with BeforeAndAfterAll {

  val SIZE: Point = Point(5, 5)
  val START_POINT: Point = Point(1, 3)
  val START_STATUS: Status = Status(START_POINT, Direction("N"))
  val INSTRUCTIONS_STRING_LIST = List("D", "A", "G", "A")
  val INSTRUCTIONS: List[Instruction] = appendInstructions(
    INSTRUCTIONS_STRING_LIST
  )
  val TONDEUSE: Tondeuse = Tondeuse(START_STATUS, INSTRUCTIONS)
  val STARTER: Starter = Starter(SIZE, List(TONDEUSE))

  def appendInstructions(insList: List[String]): List[Instruction] = {
    QueryOnLocal.extractInstructions(insList, Nil)
  }

  test("") {
    val result = MoveRobots(STARTER)
    assert(result.size == SIZE)
    assert(result.tondeuseResult.length == 1)
    assert(result.tondeuseResult(0).startStatus.coordinates.x == START_POINT.x)
    assert(result.tondeuseResult(0).startStatus.coordinates.y == START_POINT.y)
    assert(result.tondeuseResult(0).startStatus.direction == Direction("N"))
    assert(result.tondeuseResult(0).instructions == INSTRUCTIONS)
    assert(result.tondeuseResult(0).endStatus.coordinates.x == 2)
    assert(result.tondeuseResult(0).endStatus.coordinates.y == 4)
    assert(result.tondeuseResult(0).endStatus.direction == Direction("N"))
  }
}
