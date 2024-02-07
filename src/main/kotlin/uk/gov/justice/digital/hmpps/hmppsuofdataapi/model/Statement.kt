package uk.gov.justice.digital.hmpps.hmppsuofdataapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.LocalDateTime
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Statement as StatementDto

@Entity
data class Statement(
  @Id
  val id: Long,
  val reportId: Long,
  val userId: String?,
  val name: String?,
  val email: String?,
  val submittedDate: LocalDateTime?,
  val statementStatus: String,
  val lastTrainingMonth: Int?,
  val lastTrainingYear: Int?,
  val jobStartYear: Int?,
  val statement: String?,
  val staffId: Int?,
  val createdDate: LocalDateTime,
  val updatedDate: LocalDateTime?,
  val nextReminderDate: LocalDateTime?,
  val overdueDate: LocalDateTime?,
  val inProgress: Boolean,
  val deleted: LocalDateTime?,
  val removalRequestedReason: String?,
  val removalRequestedDate: LocalDateTime?,

  @OneToMany(mappedBy = "statementId")
  val statementAmendments: MutableList<StatementAmendment> = mutableListOf(),
) {
  fun toDto(): StatementDto {
    return StatementDto(
      id = id,
      reportId = reportId,
      userId = userId,
      name = name,
      email = email,
      submittedDate = submittedDate,
      statementStatus = statementStatus,
      lastTrainingMonth = lastTrainingMonth,
      lastTrainingYear = lastTrainingYear,
      jobStartYear = jobStartYear,
      statement = statement,
      staffId = staffId,
      createdDate = createdDate,
      updatedDate = updatedDate,
      nextReminderDate = nextReminderDate,
      overdueDate = overdueDate,
      inProgress = inProgress,
      deleted = deleted,
      removalRequestedReason = removalRequestedReason,
      removalRequestedDate = removalRequestedDate,
      statementAmendments = statementAmendments.map { it.toDto() }.toMutableList(),
    )
  }
}
