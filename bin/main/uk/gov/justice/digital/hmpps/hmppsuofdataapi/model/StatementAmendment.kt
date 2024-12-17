package uk.gov.justice.digital.hmpps.hmppsuofdataapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.StatementAmendment as StatementAmendmentDto

@Entity(name = "statement_amendments")
data class StatementAmendment(
  @Id
  val id: Long,
  val statementId: Long,
  val additionalComment: String,
  val dateSubmitted: LocalDateTime,
  val deleted: LocalDateTime?,
) {
  fun toDto(): StatementAmendmentDto {
    return StatementAmendmentDto(
      id = id,
      statementId = statementId,
      additionalComment = additionalComment,
      dateSubmitted = dateSubmitted,
      deleted = deleted,
    )
  }
}
