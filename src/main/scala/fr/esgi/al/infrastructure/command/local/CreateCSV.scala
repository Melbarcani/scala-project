package fr.esgi.al.infrastructure.command.local

import fr.esgi.al.domaine.Model
import fr.esgi.al.domaine.Model.{Result, TondeuseResult}
import fr.esgi.al.infrastructure.utils.Instructions.Instruction

import scala.annotation.tailrec

object CreateCSV {

  private val SEPARATOR = ";"
  private val NEW_LINE = "\n"
  private val NUMERO = "numéro"
  private val DEBUT_X = "début_x"
  private val DEBUT_Y = "début_y"
  private val DEBUT_DIRECTION = "début_direction"
  private val FIN_X = "fin_x"
  private val FIN_Y = "fin_y"
  private val FIN_DIRECTION = "fin_direction"
  private val INSTRUCTIONS = "instructions"

  def execute(result: Result): String =
    createHeaderCSV() + convertToCSVResult(result)

  def createHeaderCSV(): String = {
    addElementWithSeparator(NUMERO) + addElementWithSeparator(DEBUT_X) + addElementWithSeparator(
      DEBUT_Y
    ) + addElementWithSeparator(DEBUT_DIRECTION) +
      addElementWithSeparator(FIN_X) + addElementWithSeparator(FIN_Y) + addElementWithSeparator(
      FIN_DIRECTION
    ) +
      INSTRUCTIONS + NEW_LINE
  }

  def addElementWithSeparator(element: String): String = element + SEPARATOR

  def convertToCSVResult(result: Result): String =
    convertToRows(result.tondeuseResult, "", 1)

  @tailrec
  def convertToRows(
      tondeuseResult: List[TondeuseResult],
      rows: String,
      rowNumber: Int
  ): String = tondeuseResult match {
    case tondeuse :: rest =>
      convertToRows(rest, createRow(rows, rowNumber, tondeuse), rowNumber + 1)
    case _ => rows
  }

  private def createRow(
      rows: String,
      rowNumber: Int,
      tondeuse: TondeuseResult
  ): String =
    rows + rowNumber.toString + convertTondeuseToCsv(tondeuse) + NEW_LINE

  def convertTondeuseToCsv(tondeuse: TondeuseResult): String =
    convertStatusToCSV(tondeuse.startStatus) +
      convertStatusToCSV(tondeuse.endStatus) + SEPARATOR +
      convertInstructionsToCSV(tondeuse.instructions, "")

  def convertStatusToCSV(startStatus: Model.Status): String =
    SEPARATOR + startStatus.coordinates.x.toString +
      SEPARATOR + startStatus.coordinates.y.toString +
      SEPARATOR + startStatus.direction.value
  @tailrec
  def convertInstructionsToCSV(
      instructions: List[Instruction],
      csvInstructions: String
  ): String = instructions match {
    case i :: rest => convertInstructionsToCSV(rest, csvInstructions + i.value)
    case _         => csvInstructions
  }
}
