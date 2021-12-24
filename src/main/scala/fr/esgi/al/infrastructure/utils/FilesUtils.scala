package fr.esgi.al.infrastructure.utils

import better.files.File
import com.typesafe.config.{Config, ConfigFactory}

object FilesUtils {

  val conf: Config = ConfigFactory.load()

  def getInput: File = {
    File(conf.getString("application.input-file"))
  }

  def getJsonOutput: File = {
    File(conf.getString("application.output-json-file"))
  }
}
