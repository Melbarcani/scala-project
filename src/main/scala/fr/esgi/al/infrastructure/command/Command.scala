package fr.esgi.al.infrastructure.command

import fr.esgi.al.domaine.Model.Result
import fr.esgi.al.infrastructure.command.local.{
  CreateCSV,
  CreateJson,
  WriteLocalFile
}

object Command {

  class WriteFile(val f: Result => String) {
    def execute(result: Result): String = f(result)
  }

  def writeAll(result: Result): String = {
    val jsonfile = new WriteFile(writeJson).execute(result)
    val csvFile = new WriteFile(writeCSV).execute(result)
    "\n\t" + jsonfile + "\n\t" + csvFile
  }

  val writeJson: Result => String = (result: Result) => {
    val lines = CreateJson.execute(result).toString()
    WriteLocalFile.appendJsonFileResults(lines)
  }

  val writeCSV: Result => String = (result: Result) => {
    val lines = CreateCSV.execute(result)
    WriteLocalFile.appendCSVFileResults(lines)
  }

  val resultToCsv: Result => String = (result: Result) => {
    CreateCSV.execute(result)
  }

  val writeJsonWithOverwrite: Result => String = (result: Result) => {
    val lines = CreateJson.execute(result).toString()
    WriteLocalFile.overwriteJsonFileResults(lines)
  }

  val writeCSVWithOverwrite: Result => String = (result: Result) => {
    val lines = CreateCSV.execute(result)
    WriteLocalFile.overwriteCSVFileResults(lines)
  }
}
