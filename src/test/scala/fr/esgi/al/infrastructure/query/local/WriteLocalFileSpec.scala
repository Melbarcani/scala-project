package fr.esgi.al.infrastructure.query.local

import better.files.File
import fr.esgi.al.domaine.Model.{BadStarter, Point, Starter}
import fr.esgi.al.infrastructure.utils.Directions.Direction
import fr.esgi.al.infrastructure.utils.Instructions.{Avance, Gauche}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll

class WriteLocalFileSpec extends AnyFunSuite with BeforeAndAfterAll {

  val INPUT_DATA = "5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA"
  val LIGHT_LISTS_IN_LIST_DATA =
    List(List("5", "5"), List("1", "2"), List("N"), List("G", "A"))
  val LISTS_IN_LIST_DATA = List(
    List("5", "5"),
    List("1", "2", "N"),
    List("G", "A", "G", "A", "G", "A", "G", "A", "A"),
    List("3", "3", "E"),
    List("A", "A", "D", "A", "A", "D", "A", "D", "D", "A")
  )
  val REGULAR_POINT: Point = Point(5, 5)

  override def beforeAll(): Unit = {
    val f = getTestFile.createIfNotExists()
    f.overwrite(INPUT_DATA)
    println("Input test file Created at " + f.toString())
  }

  override def afterAll(): Unit = {
    if (!getTestFile.delete().exists) {
      println("Test file has been deleted")
    } else {
      println("Test file hasn't been deleted")
    }
  }

  def getTestFile: File = File("/tmp/inputTest.txt")

  test("The input file should be inputTest.txt") {
    assert(getTestFile == File("/tmp/inputTest.txt"))
  }

  test("ParseData should be " + INPUT_DATA) {
    val file = getTestFile
    assert(QueryOnLocal.parseData(file.lines.toList) == LISTS_IN_LIST_DATA)
  }

  test("The Extract Point should be Point(5,5)") {
    assert(QueryOnLocal.extractPoint(List("1", "1")) != REGULAR_POINT)
    assert(QueryOnLocal.extractPoint(List("5", "5")) == REGULAR_POINT)
    intercept[NumberFormatException] {
      QueryOnLocal.extractPoint(List("5", "a"))
    }
  }

  test("ExtractStarter should success") {

    QueryOnLocal.extractStarter(LIGHT_LISTS_IN_LIST_DATA) match {
      case Starter(a, b) =>
        assert(a == REGULAR_POINT)
        assert(b(0).status.coordinates.x == 1)
        assert(b(0).status.coordinates.y == 2)
        assert(b(0).status.direction == Direction("N"))
        assert(b(0).instructions(0) == Gauche)
        assert(b(0).instructions(0) == Avance)
      case BadStarter() => None
    }
  }

}
