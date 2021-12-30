package fr.esgi.al.domaine

import fr.esgi.al.infrastructure.utils.Directions.Direction
import fr.esgi.al.infrastructure.utils.Instructions.Instruction

object Model {
  sealed abstract class AbstractStarter
  case class Starter(size: Point, starterTondeuses: List[Tondeuse]) extends AbstractStarter
  case class BadStarter() extends AbstractStarter

  case class Tondeuse(status: Status, instructions: List[Instruction])

  case class Status(coordinates: Point, direction: Direction)

  case class Point(x: Int, y: Int)
}
