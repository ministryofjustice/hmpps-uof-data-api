package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.StatementAmendment
import java.time.LocalDateTime

class StatementAmendmentDtoTest {

  @Test
  fun `can create StatementAmendment with correct values`() {
    val amendmentId = 345L
    val statementId = 123L
    val dateSubmitted = LocalDateTime.now()
    val deleted = null
    val additionalComment = "This is an additional comment I originally said left leg, but it was the right leg"

    val statementAmendment = StatementAmendment(
      id = amendmentId,
      statementId = statementId,
      dateSubmitted = dateSubmitted,
      deleted = deleted,
      additionalComment = additionalComment,
    )

    assertThat(statementAmendment.id).isEqualTo(amendmentId)
    assertThat(statementAmendment.statementId).isEqualTo(statementId)
    assertThat(statementAmendment.dateSubmitted).isEqualTo(dateSubmitted)
    assertThat(statementAmendment.deleted).isNull()
    assertThat(statementAmendment.additionalComment).isEqualTo(additionalComment)
  }
}
