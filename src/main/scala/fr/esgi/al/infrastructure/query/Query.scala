package fr.esgi.al.infrastructure.query

trait Query {
  def parseData(): List[List[String]]

  def extractString(): String
}
