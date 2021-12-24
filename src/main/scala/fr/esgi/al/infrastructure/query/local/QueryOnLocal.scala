package fr.esgi.al.infrastructure.query.local

import better.files.File
import fr.esgi.al.infrastructure.query.Query
import fr.esgi.al.infrastructure.utils.FilesUtils

object QueryOnLocal extends Query {
  val f: File = FilesUtils.getInput

  override def extractLines(): List[String] = {
    f.lines.toList
  }

  override def extractString(): String = {
    f.contentAsString
  }

}
