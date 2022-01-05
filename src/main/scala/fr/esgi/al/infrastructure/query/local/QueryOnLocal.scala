package fr.esgi.al.infrastructure.query.local

import better.files.File
import fr.esgi.al.domaine.Model.{
  AbstractStarter,
  BadStarter,
  Point,
  Starter,
  Status,
  Tondeuse
}
import fr.esgi.al.infrastructure.command.Command
import fr.esgi.al.infrastructure.exception.DonneesIncorectesException
import fr.esgi.al.infrastructure.query.Query
import fr.esgi.al.infrastructure.utils.Directions.Direction
import fr.esgi.al.infrastructure.utils.Instructions.{Instruction, Movement}
import fr.esgi.al.infrastructure.utils.{Directions, FilesUtils, Instructions}
import fr.esgi.al.use_case.Board.MoveRobots

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object QueryOnLocal extends Query {
  val f: File = FilesUtils.getInput

  val lines: List[String] = f.lines.toList

  def extractAndStartMove(): Object = {
    extract(parseData(lines)) match {
      case Success(starter) => startMove(starter)
      case Failure(a) =>
        DonneesIncorectesException("Incorrect data => " + a.getMessage)
    }
  }

  def extract(data: List[List[String]]): Try[AbstractStarter] =
    Try(extractStarter(data))

  def parseData(lines: List[String]): List[List[String]] =
    parseLines(lines.drop(1), Nil)
      .patch(0, List(parseFirstLine(lines(0), Nil)), 0)

  @tailrec
  def parseFirstLine(firstLine: String, list: List[String]): List[String] =
    firstLine match {
      case s if s.startsWith(" ") && s.length > 1 =>
        list.appended(s.substring(1, s.length))
      case s if s.length > 1 =>
        parseFirstLine(firstLine.substring(1), list.appended(s.substring(0, 1)))
      case _ => list
    }

  @tailrec
  def parseLines(
      lines: List[String],
      dataList: List[List[String]]
  ): List[List[String]] = lines match {
    case line :: otherLines =>
      parseLines(otherLines, dataList.appended(parseOneLine(line, Nil)))
    case _ => dataList
  }

  @tailrec
  def parseOneLine(line: String, list: List[String]): List[String] =
    line match {
      case s if s.startsWith(" ") && s.length > 1 =>
        parseOneLine(line.substring(1), list)
      case s if s.length > 1 =>
        parseOneLine(line.substring(1), list.appended(s.substring(0, 1)))
      case s => list.appended(s)
    }

  def startMove(starter: AbstractStarter): Object = starter match {
    case Starter(a, b) =>
      val moveResult = MoveRobots(Starter(a, b))
      Command.writeAll(moveResult)
    case _ =>
      DonneesIncorectesException(
        "Incorrect data => Entered data is not correct"
      )
  }

  def extractStarter(data: List[List[String]]): AbstractStarter = data match {
    case a :: r if isBoardSizeCorrect(a) && primaryCheck(r, verified = false) =>
      Starter(extractPoint(a), extractTondeuses(r, Nil))
    case _ => BadStarter()
  }

  def isBoardSizeCorrect(coordinates: List[String]): Boolean =
    isNumber(coordinates) && coordinates.length == 2

  def isNumber(coordinates: List[String]): Boolean =
    coordinates.forall(s => Try(s.toInt).isSuccess)

  @tailrec
  def primaryCheck(data: List[List[String]], verified: Boolean): Boolean =
    data match {
      case status :: instructions :: next =>
        status.length == 3 &&
          Directions.values.contains(status(2)) &&
          instructions.forall(i => Instructions.values.contains(i)) &&
          primaryCheck(next, verified = true)
      case _ => verified
    }

  @tailrec
  def extractTondeuses(
      listInputTondeuses: List[List[String]],
      starters: List[Tondeuse]
  ): List[Tondeuse] =
    (listInputTondeuses, starters) match {
      case (status :: instructions :: nextTondeuses, _) =>
        extractTondeuses(
          nextTondeuses,
          starters.appendedAll(
            List(
              Tondeuse(
                Status(extractPoint(status), Direction(status(2))),
                extractInstructions(instructions, Nil)
              )
            )
          )
        )
      case (_, Nil) => Nil
      case (_, _)   => starters
    }

  @tailrec
  def extractInstructions(
      instructionsInput: List[String],
      instructions: List[Instruction]
  ): List[Instruction] = instructionsInput match {
    case ins :: rest =>
      extractInstructions(rest, instructions.appended(Movement.apply(ins)))
    case Nil => instructions
  }

  def extractPoint(l: List[String]): Point = Point(l(0).toInt, l(1).toInt)

}
