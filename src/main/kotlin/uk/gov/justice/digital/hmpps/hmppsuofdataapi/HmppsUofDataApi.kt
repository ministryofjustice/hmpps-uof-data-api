package uk.gov.justice.digital.hmpps.hmppsuofdataapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HmppsUofDataApi

fun main(args: Array<String>) {
  runApplication<HmppsUofDataApi>(*args)
}
