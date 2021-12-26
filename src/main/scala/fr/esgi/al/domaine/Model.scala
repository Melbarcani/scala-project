package fr.esgi.al.domaine

object Model {
  sealed abstract class AbstractStarter
  case class Starter(size: Point, starterTondeuses: List[Tondeuse]) extends AbstractStarter
  case class BadStarter() extends AbstractStarter

  case class Tondeuse(status: Status, instruction: Instruction)

  sealed abstract class AbstractStatus
  case class Status(point: Point, direction: String) extends AbstractStatus
  case class BadStatus() extends AbstractStatus

  sealed abstract class AbstractInstructions
  case class Instruction(list: List[String]) extends AbstractInstructions
  case class BadInstruction() extends AbstractInstructions

  case class Point(x: Int, y: Int)
}
