package fr.esgi.al.use_case

import fr.esgi.al.domaine.Model.{
  Point,
  Result,
  Starter,
  Status,
  Tondeuse,
  TondeuseResult
}
import fr.esgi.al.infrastructure.utils.Directions.{
  Direction,
  East,
  North,
  South
}
import fr.esgi.al.infrastructure.utils.Instructions
import fr.esgi.al.infrastructure.utils.Instructions.{
  Avance,
  Droite,
  Gauche,
  Instruction
}

import scala.annotation.tailrec

object Board {

  case class MoveRobots(starter: Starter)

  object MoveRobots {

    def apply(starter: Starter): Result =
      Result(
        starter.size,
        moveOneByOneTondeuse(starter.size, starter.starterTondeuses, Nil)
      )

    @tailrec
    def moveOneByOneTondeuse(
        size: Point,
        listStarterTondeuses: List[Tondeuse],
        listeResultTondeuses: List[TondeuseResult]
    ): List[TondeuseResult] = listStarterTondeuses match {
      case tondeuse :: rest =>
        moveOneByOneTondeuse(
          size,
          rest,
          listeResultTondeuses.appended(
            TondeuseResult(
              tondeuse.status,
              tondeuse.instructions,
              moveTondeuseList(size, tondeuse.status, tondeuse.instructions)
            )
          )
        )
      case Nil => listeResultTondeuses
    }

    @tailrec
    def moveTondeuseList(
        size: Point,
        status: Status,
        instructions: List[Instruction]
    ): Status = instructions match {
      case Gauche :: rest =>
        moveTondeuseList(
          size,
          Status(
            status.coordinates,
            Direction.turn(Instructions.Gauche, status.direction)
          ),
          rest
        )
      case Droite :: rest =>
        moveTondeuseList(
          size,
          Status(
            status.coordinates,
            Direction.turn(Instructions.Droite, status.direction)
          ),
          rest
        )
      case Avance :: rest =>
        moveTondeuseList(size, justMove(size, status), rest)
      case _ => status
    }

    def justMove(size: Point, status: Status): Status = status.direction match {
      case North =>
        Status(
          coordinates = Point(
            status.coordinates.x,
            new MoveBot(farFromOrigin).execute(size.y, status.coordinates.y)
          ),
          direction = status.direction
        )
      case East =>
        Status(
          Point(
            new MoveBot(farFromOrigin).execute(size.x, status.coordinates.x),
            status.coordinates.y
          ),
          status.direction
        )
      case South =>
        Status(
          coordinates = Point(
            status.coordinates.x,
            new MoveBot(toOrigin).execute(size.y, status.coordinates.y)
          ),
          status.direction
        )
      case _ =>
        Status(
          coordinates = Point(
            new MoveBot(toOrigin).execute(size.x, status.coordinates.x),
            status.coordinates.y
          ),
          status.direction
        )
    }

    class MoveBot(val f: (Int, Int) => Int) {
      def execute(sizeCoordinate: Int, currentCoordinate: Int): Int =
        f(sizeCoordinate, currentCoordinate)
    }

    val farFromOrigin: (Int, Int) => Int = (sizeX: Int, currentX: Int) =>
      if (currentX + 1 > sizeX) sizeX else currentX + 1
    val toOrigin: (Int, Int) => Int = (sizeX: Int, currentX: Int) =>
      if (currentX - 1 < sizeX - sizeX) 0 else currentX - 1

  }
}
