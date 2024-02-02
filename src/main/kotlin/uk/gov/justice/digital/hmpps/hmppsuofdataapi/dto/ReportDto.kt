package uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Use of Force Report")
@JsonInclude(ALWAYS)
data class Report(
  @Schema(description = "The ID of the report", example = "691")
  val id: Long,

  @Schema(description = "sequenceNo", example = "2")
  val sequenceNo: Int,

  @Schema(description = "The date and time at which the report was created", example = "2024-01-29 18:09:42.077879+00")
  val createdDate: LocalDateTime,

  @Schema(description = "The date and time at which the report was last updated", example = "2024-01-29 18:09:42.077879+00")
  val updatedDate: LocalDateTime?,

  @Schema(description = "User provided date and time at which the incident took place (minute accuracy only)", example = "2024-01-02 14:12:00+00")
  val incidentDate: LocalDateTime?,

  @Schema(description = "The date and time at which the report was submitted for review. Null if the report has not been submitted", example = "2024-01-29 18:09:42.077879+00")
  val submittedDate: LocalDateTime?,

  @Schema(description = "The time at which a report is marked deleted. Null if the report has not been deleted", example = "2024-01-30 18:09:42.077879+00")
  val deleted: LocalDateTime?,

  @Schema(description = "Current status of the report", example = "SUBMITTED")
  val status: String?,

  @Schema(description = "NOMIS Agency ID", example = "BAI")
  val agencyId: String?,

  @Schema(description = "ID of the user that created the report", example = "SMITHJ")
  val userId: String?,

  @Schema(description = "Full name of the user that created the report", example = "John Smith")
  val reporterName: String,

  @Schema(description = "NOMIS Prison number of the subject of the use of force", example = "G8133UA")
  val offenderNo: String,

  @Schema(description = "NOMIS bookingId", example = "889765")
  val bookingId: Long,

  @Schema(description = "The JSON blob holding the answers provided in the Use of Force form.", example = "{}")
  val formResponse: Map<String, Any?>?,

  @Schema(description = "The witness statements associated to the report", example = "The prisoner hit me so he was restrained")
  val statements: List<Statement>?,
)

@Schema(description = "Use of Force Statement")
@JsonInclude(ALWAYS)
data class Statement(
  @Schema(description = "id", example = "")
  val id: Long,

  @Schema(description = "reportId", example = "")
  val reportId: Long,

  @Schema(description = "createdDate", example = "")
  val createdDate: LocalDateTime,

  @Schema(description = "updatedDate", example = "")
  val updatedDate: LocalDateTime?,

  @Schema(description = "submittedDate", example = "")
  val submittedDate: LocalDateTime?,

  @Schema(description = "The time at which a statement is marked deleted. Null if the statement has not been deleted", example = "2024-01-30 18:09:42.077879+00")
  val deleted: LocalDateTime?,

  @Schema(description = "nextReminderDate", example = "")
  val nextReminderDate: LocalDateTime?,

  @Schema(description = "overdueDate", example = "")
  val overdueDate: LocalDateTime?,

  @Schema(description = "removalRequestedDate", example = "")
  val removalRequestedDate: LocalDateTime?,

  @Schema(description = "userId", example = "")
  val userId: String?,

  @Schema(description = "name", example = "")
  val name: String?,

  @Schema(description = "email", example = "")
  val email: String?,

  @Schema(description = "statementStatus", example = "")
  val statementStatus: String,

  @Schema(description = "lastTrainingMonth", example = "")
  val lastTrainingMonth: Int?,

  @Schema(description = "lastTrainingYear", example = "")
  val lastTrainingYear: Int?,

  @Schema(description = "jobStartYear", example = "")
  val jobStartYear: Int?,

  @Schema(description = "staffId", example = "")
  val staffId: Int?,

  @Schema(description = "inProgress", example = "")
  val inProgress: Boolean,

  @Schema(description = "removalRequestedReason", example = "")
  val removalRequestedReason: String?,

  @Schema(description = "statement", example = "")
  val statement: String?,

  @Schema(description = "Amendments (additional comments) made to the statement", example = "I originally said left leg, but it was the right leg")
  val statementAmendments: MutableList<StatementAmendment>,
)

@Schema(description = "Use of Force Statement Amendment")
@JsonInclude(ALWAYS)
data class StatementAmendment(
  @Schema(description = "id of the statement amendment", example = "345")
  val id: Long,

  @Schema(description = "ID of the statement being amended", example = "")
  val statementId: Long,

  @Schema(description = "The date and time at which the additional comment was submitted", example = "")
  val dateSubmitted: LocalDateTime,

  @Schema(description = "The date and time at which the additional comment was deleted. Null if not deleted", example = "")
  val deleted: LocalDateTime?,

  @Schema(description = "additionalComment", example = "")
  val additionalComment: String,
)
