package fr.esgi.al.infrastructure.query.local

import better.files.File
import fr.esgi.al.domaine.Model.{AbstractStarter, BadStarter, Instruction, Point, Starter, Status, Tondeuse}
import fr.esgi.al.infrastructure.exception.DonneesIncorectesException
import fr.esgi.al.infrastructure.query.Query
import fr.esgi.al.infrastructure.utils.Constants.{VALID_INSTRUCTIONS, VALID_ORIENTATIONS}
import fr.esgi.al.infrastructure.utils.FilesUtils

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object QueryOnLocal extends Query {
  val f: File = FilesUtils.getInput

  val lines: List[String] = f.lines.toList

  def parseData(): List[List[String]] = parseLines(lines.drop(1), Nil).patch(0, List(parseFirstLine(lines(0))), 0)

  def parseFirstLine(firstLine: String): List[String] =  parseFirstLineHelper(firstLine, Nil)

  @tailrec
  def parseFirstLineHelper(firstLine: String, list: List[String]): List[String] = firstLine match {
    case s if s.startsWith(" ") && s.length > 1=> list.appended(s.substring(1, s.length))
    case s if s.length > 1 => parseFirstLineHelper(firstLine.substring(1), list.appended(s.substring(0,1)))
    case _ => list
  }

  @tailrec
  def parseLines(lines: List[String], dataList: List[List[String]]): List[List[String]] = lines match {
    case line :: otherLines =>
      parseLines(otherLines, dataList.appended(parseOneLine(line, Nil)))
    case _ => dataList
  }


  @tailrec
  def parseOneLine(line: String, list: List[String]): List[String] = line match {
    case s if s.startsWith(" ") && s.length > 1=> parseOneLine(line.substring(1), list)
    case s if s.length > 1 => parseOneLine(line.substring(1), list.appended(s.substring(0,1)))
    case s => list.appended(s)
  }


  def extractData(): Object = {
    extract(parseData()) match {
      case Success(starter) => checkFailure(starter)
      case Failure(a) => DonneesIncorectesException("Incorrect data => " + a.getMessage)
    }
  }

  def checkFailure(starter: AbstractStarter): Object = starter match {
    case BadStarter() => DonneesIncorectesException("Incorrect data => Entered data is not correct")
    case _ => starter
  }

  def extract(data: List[List[String]]): Try[AbstractStarter] = Try(extractStarter(data))


  def extractStarter(data: List[List[String]]): AbstractStarter = data match {
    case a :: r if isBoardSizeCorrect(a) && primaryCheck(r) => Starter(extractPoint(a), extractTondeuses(r))
    case _ => BadStarter()
  }

  def isNumber(coordinates: List[String]) = coordinates.forall(s => {
    s.charAt(0).toInt > 0
  })

  def isBoardSizeCorrect(coordinates: List[String]): Boolean = isNumber(coordinates) && coordinates.length == 2

  def primaryCheck(data: List[List[String]]): Boolean = primaryCheckHelper(data, verified = false)

  @tailrec
  def primaryCheckHelper(data: List[List[String]], verified: Boolean): Boolean = data match {
    case status :: instructions :: next =>
      status.length == 3 &&
        VALID_ORIENTATIONS.contains(status(2)) &&
        instructions.forall(i => VALID_INSTRUCTIONS.contains(i)) &&
        primaryCheckHelper(next, verified = true)
    case _ => verified
  }

  def extractTondeuses(listInputTondeuse: List[List[String]]): List[Tondeuse] =
    extractTondeusesHelper(listInputTondeuse, Nil)

  @tailrec
  def extractTondeusesHelper(listInputTondeuses: List[List[String]], starters: List[Tondeuse]): List[Tondeuse] =
    (listInputTondeuses, starters) match {
      case (status :: instructions :: nextTondeuses, _) =>
        extractTondeusesHelper(nextTondeuses, starters.appendedAll(List(Tondeuse(extractStatus(status),
          Instruction(instructions)))))
      case (_, Nil) => Nil
      case (_, _) => starters
    }

  def extractPoint(l: List[String]): Point = Point(l(0).toInt, l(1).toInt)

  def extractStatus(s: List[String]): Status = Status(extractPoint(s), s(2))

  @tailrec
  def checkAndExtractInstruction(instructions: List[Char], verifiedInstructions: List[String]): List[String] = instructions match {
    case i :: r if VALID_INSTRUCTIONS.contains(i.toString) => checkAndExtractInstruction(r, verifiedInstructions.appended(i.toString))
    case i :: Nil if VALID_INSTRUCTIONS.contains(i.toString) => verifiedInstructions.appended(i.toString)
    case _ => verifiedInstructions
  }

  override def extractString(): String = {
    f.contentAsString
  }

}
