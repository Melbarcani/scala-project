package fr.esgi.al.infrastructure.utils

import better.files.File
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.funsuite.AnyFunSuite

class FilesUtilsSpec extends AnyFunSuite {

  val conf: Config = ConfigFactory.load()

  test("The input file should be input.txt") {
    assert(FilesUtils.getInput != File("bad/path"))
    assert(FilesUtils.getInput == File("/tmp/input.txt"))
  }

  test("The json output file should be output.json") {
    assert(FilesUtils.getJsonOutput != File("bad/file"))
    assert(FilesUtils.getJsonOutput == File("/tmp/output.json"))
  }

  test("The csv output file should be output.csv") {
    assert(FilesUtils.getCSVOutput != File("bad/file"))
    assert(FilesUtils.getCSVOutput == File("/tmp/output.csv"))
  }

}
