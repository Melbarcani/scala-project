package fr.esgi.al.use_case

import fr.esgi.al.domaine.Model.{Starter}

object Board {

  case class MoveRobots(starter: Starter)

  object MoveRobots {

    /*def apply(starter: Starter): List[Tondeuse] = moveTondeuses(starter.size, starter.starterTondeuses)

    def moveTondeuses(size: Point, starterTondeuses: List[Tondeuse]): List[Tondeuse] = moveOneByOneTondeuse(size, starterTondeuses, Nil)

    def moveOneByOneTondeuse(size: Point
                             , listStarterTondeuses: List[Tondeuse]
                             , listeResultTondeuses: List[Tondeuse]): List[Tondeuse] = listStarterTondeuses match {
      case head :: rest => moveOneByOneTondeuse(size, rest, move(size, head, listeResultTondeuses))
      case Nil => listeResultTondeuses
    }

    def move(size: Point, tondeuse: Tondeuse, listeResultTondeuses: List[Tondeuse]): List[Tondeuse] = listeResultTondeuses.appended(moveTondeuse(size, tondeuse))

    def moveTondeuse(size: Point, tondeuse: Tondeuse): Tondeuse = {

    }

    class MoveBot(val f: (Status, Instruction) => Status) {
      def execute(status: Status, instruction: Instruction) = f(status, instruction)
    }

    val moveForward = (s: Status, i: Instruction) => {

    }*/
  }
}
