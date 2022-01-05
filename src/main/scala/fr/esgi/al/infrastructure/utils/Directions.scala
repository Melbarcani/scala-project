package fr.esgi.al.infrastructure.utils

import fr.esgi.al.infrastructure.utils.Directions.Direction.{
  EAST,
  NORTH,
  SOUTH,
  WEST
}
import fr.esgi.al.infrastructure.utils.Instructions.{
  Droite,
  Gauche,
  Instruction
}

object Directions {

  sealed abstract class Direction(val value: String) extends Enumeration {}

  case object North extends Direction(NORTH)

  case object East extends Direction(EAST)

  case object South extends Direction(SOUTH)

  case object West extends Direction(WEST)

  val values = List(NORTH, EAST, SOUTH, WEST)

  object Direction {
    val NORTH = "N"
    val EAST = "E"
    val SOUTH = "S"
    val WEST = "W"

    def apply(d: String): Direction = d match {
      case NORTH => North
      case EAST  => East
      case SOUTH => South
      case _     => West
    }

    def turn(i: Instruction, d: Direction): Direction = (i, d) match {
      case (Gauche, North) | (Droite, South) => Direction(WEST)
      case (Gauche, East) | (Droite, West)   => Direction(NORTH)
      case (Gauche, South) | (Droite, North) => Direction(EAST)
      case (_, _)                            => Direction(SOUTH)
    }
  }

}
