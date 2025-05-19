package uk.gov.justice.digital.hmpps.hmppsuofdataapi.model

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnTransformer
import java.time.LocalDateTime
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Report as ReportDto

@Entity
@Table(name = "report")
class ReportDetail(
  id: Long,
  @ColumnTransformer(write = "?::jsonb")
  private val formResponse: String,
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

  @OneToMany(mappedBy = "reportId")
  val statements: MutableList<Statement> = mutableListOf(),

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
    formResponse = if (includeFormResponse == true) toJson(formResponse) else null,
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
    statements = if (includeStatements == true) statements.map { it.toDto() } else null,
  )

  private fun toJson(formResponse: String): Map<String, Any?> = ObjectMapper().readValue(formResponse, object : TypeReference<Map<String, Any?>?>() {}) ?: mapOf()
}
