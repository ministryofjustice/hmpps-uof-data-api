package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.dto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Statement
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.StatementAmendment

class StatementDtoTest {

    @Test
    fun `can create Statement with correct values`() {

        val statementId = 1L
        val reportId = 2L
        val createdDate = LocalDateTime.now()
        val updatedDate = LocalDateTime.now()
        val submittedDate = LocalDateTime.now()
        val deleted = null
        val nextReminderDate = null
        val overdueDate = null
        val removalRequestedDate = null
        val userId = "aleman123"
        val name = "John Ale"
        val email = "john.ale@justice.gov.uk"
        val statementStatus = "PENDING"
        val lastTrainingMonth = 7
        val lastTrainingYear = 2023
        val jobStartYear = 2020
        val staffId = 123
        val inProgress = true
        val removalRequestedReason = "Some reason"
        val statementText = "This is a statement."
        val statementAmendments = mutableListOf<StatementAmendment>()

        val statement = Statement(
            id = statementId,
            reportId = reportId,
            createdDate = createdDate,
            updatedDate = updatedDate,
            submittedDate = submittedDate,
            deleted = deleted,
            nextReminderDate = nextReminderDate,
            overdueDate = overdueDate,
            removalRequestedDate = removalRequestedDate,
            userId = userId,
            name = name,
            email = email,
            statementStatus = statementStatus,
            lastTrainingMonth = lastTrainingMonth,
            lastTrainingYear = lastTrainingYear,
            jobStartYear = jobStartYear,
            staffId = staffId,
            inProgress = inProgress,
            removalRequestedReason = removalRequestedReason,
            statement = statementText,
            statementAmendments = statementAmendments
        )

        assertThat(statement.id).isEqualTo(statementId)
        assertThat(statement.reportId).isEqualTo(reportId)
        assertThat(statement.createdDate).isEqualTo(createdDate)
        assertThat(statement.updatedDate).isEqualTo(updatedDate)
        assertThat(statement.submittedDate).isEqualTo(submittedDate)
        assertThat(statement.deleted).isNull()
        assertThat(statement.nextReminderDate).isNull()
        assertThat(statement.overdueDate).isNull()
        assertThat(statement.removalRequestedDate).isNull()
        assertThat(statement.userId).isEqualTo(userId)
        assertThat(statement.name).isEqualTo(name)
        assertThat(statement.email).isEqualTo(email)
        assertThat(statement.statementStatus).isEqualTo(statementStatus)
        assertThat(statement.lastTrainingMonth).isEqualTo(lastTrainingMonth)
        assertThat(statement.lastTrainingYear).isEqualTo(lastTrainingYear)
        assertThat(statement.jobStartYear).isEqualTo(jobStartYear)
        assertThat(statement.staffId).isEqualTo(staffId)
        assertThat(statement.inProgress).isTrue
        assertThat(statement.removalRequestedReason).isEqualTo(removalRequestedReason)
        assertThat(statement.statement).isEqualTo(statementText)
        assertThat(statement.statementAmendments).isEqualTo(statementAmendments)
    }
}
