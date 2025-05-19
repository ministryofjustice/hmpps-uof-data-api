package uk.gov.justice.digital.hmpps.hmppsuofdataapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Report as ReportDto

@Entity
@Table(name = "report")
open class ReportSummary(
  id: Long,
  userId: String,
  sequenceNo: Int,
  bookingId: Long,
  createdDate: LocalDateTime,
  status: String,
  submittedDate: LocalDateTime?,
  offenderNo: String,
  reporterName: String,
  incidentDate: LocalDateTime,
  agencyId: String,
  updatedDate: LocalDateTime,
  deleted: LocalDateTime?,
) : Report(
  id,
  userId,
  sequenceNo,
  bookingId,
  createdDate,
  status,
  submittedDate,
  offenderNo,
  reporterName,
  incidentDate,
  agencyId,
  updatedDate,
  deleted,
) {

  override fun toDto(includeStatements: Boolean?, includeFormResponse: Boolean?): ReportDto = ReportDto(
    id = id,
    formResponse = null,
    userId = userId,
    sequenceNo = sequenceNo,
    bookingId = bookingId,
    createdDate = createdDate,
    status = status,
    submittedDate = submittedDate,
    offenderNo = offenderNo,
    reporterName = reporterName,
    incidentDate = incidentDate,
    agencyId = agencyId,
    updatedDate = updatedDate,
    deleted = deleted,
    statements = null,
  )
}
