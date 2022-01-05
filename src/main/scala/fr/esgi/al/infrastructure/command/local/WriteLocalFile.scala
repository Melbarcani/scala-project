package fr.esgi.al.infrastructure.command.local

import better.files.{File, UnicodeCharset}
import fr.esgi.al.infrastructure.utils.FilesUtils
import java.nio.charset.Charset

object WriteLocalFile {

  val jsonFileOutput: File = FilesUtils.getJsonOutput
  val csvFileOutput: File = FilesUtils.getCSVOutput

  def overwriteJsonFileResults(lines: String): String =
    jsonFileOutput.createIfNotExists().overwrite(lines).toString()

  def appendJsonFileResults(lines: String): String =
    jsonFileOutput.createIfNotExists().appendLine(lines).toString()

  def overwriteCSVFileResults(lines: String): String =
    csvFileOutput
      .createIfNotExists()
      .overwrite(lines)(
        charset =
          UnicodeCharset(Charset.forName("UTF-8"), writeByteOrderMarkers = true)
      )
      .toString()

  def appendCSVFileResults(lines: String): String =
    csvFileOutput
      .createIfNotExists()
      .appendLine(lines)(
        charset =
          UnicodeCharset(Charset.forName("UTF-8"), writeByteOrderMarkers = true)
      )
      .toString()
}
