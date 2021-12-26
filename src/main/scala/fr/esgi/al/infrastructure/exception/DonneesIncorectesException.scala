package fr.esgi.al.infrastructure.exception


final case class DonneesIncorectesException(private val message: String )
  extends RuntimeException(message)
