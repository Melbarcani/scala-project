package fr.esgi.al.infrastructure.query

trait Query {
  def extractLines(): List[String]

  def extractString(): String
}
