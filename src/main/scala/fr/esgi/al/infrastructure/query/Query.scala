package fr.esgi.al.infrastructure.query

trait Query {
  def parseData(lines: List[String]): List[List[String]]
}
