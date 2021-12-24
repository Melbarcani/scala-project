package fr.esgi.al.funprog

import play.api.libs.json.{Json, Reads, Writes}

import java.time.LocalDate

object Exercice4 extends App {

  /*trait Writes[T] {
    def writes(e: T): JsValue
  }

  object Writes {
    def apply[A](implicit w: Writes[A]): Writes[A] = w

    implicit val intWrites = new Writes[Int] {
      def writes(e: Int): JsValue = JsNumber(e)
    }

    implicit val boolWrites = new Writes[Boolean] {
      def writes(e: Boolean): JsValue = JsBoolean(e)
    }

    implicit val stringWrites = new Writes[String] {
      def writes(e: String): JsValue = JsString(e)
    }

    implicit val localDateWrite = new Writes[LocalDate] {
      def writes(e: LocalDate): JsValue = JsNumber{BigDecimal(e.toEpochDay)}
    }
  }*/

  case class Person(id: Int, name: String, isFemale: Boolean, info: PersonalInfo)

  case class PersonalInfo(birthDate: LocalDate, weigth: Int, height: Int)

  object PersonalInfo {
    implicit val writes: Writes[PersonalInfo] = Json.writes[PersonalInfo]
    implicit val reads: Reads[PersonalInfo] = Json.reads[PersonalInfo]
    /*implicit def personalInfoWriter = new Writes[PersonalInfo] {
      override def writes(e: PersonalInfo): JsValue = JsObject {
        Map{
          "birthday" -> Writes[LocalDate].writes(e.birthDate)
          "weight" -> Writes[Int].writes(e.weigth)
          "height" -> Writes[Int].writes(e.height)
        }
      }
    }*/
  }

  object Person {
    implicit val writes: Writes[Person] = Json.writes[Person]
    implicit val reads: Reads[Person] = Json.reads[Person]

    /*implicit def personWriter = new Writes[Person] {
      override def writes(e: Person): JsValue = JsObject{
        Map{
          "id" -> Writes[Int].writes(e.id)
          "name" -> Writes[String].writes(e.name)
          "isFemale" -> Writes[Boolean].writes(e.isFemale)
          "personalInfo" -> Writes[PersonalInfo].writes(e.info)
        }
      }
    }*/
  }

  sealed trait Employee

  case class Manager(id: Int, person: Person) extends Employee

  case class Worker(id: Int, managerId: Int, person: Person) extends Employee

  object Manager {
    implicit val writes: Writes[Manager] = Json.writes[Manager]
    implicit val reads: Reads[Manager] = Json.reads[Manager]
  }

  object worker {
    implicit val writes: Writes[Worker] = Json.writes[Worker]
    implicit val reads: Reads[Worker] = Json.reads[Worker]
  }

  val w: Writes[Person] = implicitly[Writes[Person]]
  val r: Reads[Person] = implicitly[Reads[Person]]

  val person = Person(1, "John Doe", false, PersonalInfo(LocalDate.now(), 89, 186))

  print(r.reads(w.writes(person)))
}