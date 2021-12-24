package fr.esgi.al.infrastructure.utils

import better.files.File
import com.typesafe.config.{Config, ConfigFactory}

object FilesUtils {

  val conf: Config = ConfigFactory.load()

  def getInput: File = {
    val input = conf.getString("application.input-file")
    File(input) // using constructor
  }
}
