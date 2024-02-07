package uk.gov.justice.digital.hmpps.hmppsuofdataapi.model

import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Report as ReportDto

@MappedSuperclass
abstract class Report(
  @Id
  open val id: Long,
  open val userId: String,
  open val sequenceNo: Int,
  open val bookingId: Long,
  open val createdDate: LocalDateTime,
  open val status: String,
  open val submittedDate: LocalDateTime?,
  open val offenderNo: String,
  open val reporterName: String,
  open val incidentDate: LocalDateTime,
  open val agencyId: String,
  open val updatedDate: LocalDateTime,
  open val deleted: LocalDateTime?,
) {

  abstract fun toDto(includeStatements: Boolean? = true, includeFormResponse: Boolean? = true): ReportDto
}
