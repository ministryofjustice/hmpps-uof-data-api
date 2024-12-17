package uk.gov.justice.digital.hmpps.hmppsuofdataapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
const val SYSTEM_USERNAME = "UOF_DATA_API"

@SpringBootApplication
class HmppsUofDataApi

fun main(args: Array<String>) {
  runApplication<HmppsUofDataApi>(*args)
}
