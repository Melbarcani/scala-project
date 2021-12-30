package fr.esgi.al.infrastructure.utils

import fr.esgi.al.infrastructure.utils.Instructions.Movement.{AVANCE, DROITE, GAUCHE}

object Instructions {
  sealed abstract class Instruction(val value: String) extends Enumeration

  case object Avance extends Instruction("A")

  case object Gauche extends Instruction("G")

  case object Droite extends Instruction("D")

  val values = List(AVANCE, GAUCHE, DROITE)

  object Movement {
    val AVANCE = "A"
    val GAUCHE = "G"
    val DROITE = "D"

    def apply(ins: String): Instruction = ins match {
      case GAUCHE => Gauche
      case DROITE => Droite
      case _ => Avance
    }
  }
}
