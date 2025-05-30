package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.resource

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.IntegrationTestBase
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportDetail
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportSummary
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.StatementAmendment
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.StatementAmendmentRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.StatementRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.Statement as StatementDataClass

class ReportResourceIT : IntegrationTestBase() {

  @Autowired
  lateinit var repo: ReportRepository

  @Autowired
  lateinit var statementRepository: StatementRepository

  @Autowired
  lateinit var statementAmendmentRepository: StatementAmendmentRepository

  lateinit var expectedReport: ReportSummary
  lateinit var expectedReportwithStatements: ReportSummary

  final val statementAmendment = StatementAmendment(
    id = 1L,
    statementId = 1,
    dateSubmitted = LocalDateTime.now(),
    deleted = null,
    additionalComment = "This is an additional comment I originally said left leg, but it was the right leg",
  )

  val statement = StatementDataClass(
    id = 1L,
    reportId = 2L,
    createdDate = LocalDateTime.now(),
    updatedDate = LocalDateTime.now(),
    submittedDate = LocalDateTime.now(),
    deleted = null,
    nextReminderDate = null,
    overdueDate = null,
    removalRequestedDate = null,
    userId = "aleman123",
    name = "John Ale",
    email = "john.ale@justice.gov.uk",
    statementStatus = "PENDING",
    lastTrainingMonth = 7,
    lastTrainingYear = 2023,
    jobStartYear = 2020,
    staffId = 123,
    inProgress = true,
    removalRequestedReason = "Some reason",
    statement = "This is a statement.",
    statementAmendments = mutableListOf(statementAmendment),
  )

  @BeforeEach
  fun setup() {
    repo.deleteAll()

    expectedReport = ReportSummary(
      id = 1L,
      userId = "user_id",
      sequenceNo = 1,
      bookingId = 1234L,
      createdDate = LocalDateTime.now(),
      status = "IN_PROGRESS",
      submittedDate = null,
      offenderNo = "GU1234A",
      reporterName = "reporter_name",
      incidentDate = LocalDateTime.of(2024, 1, 1, 14, 0),
      agencyId = "MDI",
      updatedDate = LocalDateTime.now(),
      deleted = null,
    )

    expectedReportwithStatements = ReportSummary(
      id = 2L,
      userId = "user_id",
      sequenceNo = 1,
      bookingId = 1235L,
      createdDate = LocalDateTime.now(),
      status = "IN_PROGRESS",
      submittedDate = null,
      offenderNo = "GU1234B",
      reporterName = "reporter_name",
      incidentDate = LocalDateTime.of(2024, 1, 1, 14, 0),
      agencyId = "MDI",
      updatedDate = LocalDateTime.now(),
      deleted = null,
    )
  }

  private fun buildReport(
    id: Long,
    offenderNumber: String,
  ): ReportDetail = ReportDetail(
    id, "{}",
    "user_id",
    1,
    1234,
    LocalDateTime.now(),
    "IN_PROGRESS",
    null,
    offenderNumber,
    "reporter_name",
    LocalDateTime.of(2024, 1, 1, 14, 0),
    "MDI",
    LocalDateTime.now(),
    null,

  )

  private fun buildReportIncludeStatements(
    id: Long,
    offenderNumber: String,
  ): ReportDetail = ReportDetail(
    id, "{}",
    "user_id",
    2,
    1235,
    LocalDateTime.now(),
    "IN_PROGRESS",
    null,
    offenderNumber,
    "reporter_name",
    LocalDateTime.of(2024, 1, 1, 14, 0),
    "MDI",
    LocalDateTime.now(),
    null,
    mutableListOf(statement),
  )

  @DisplayName("GET /report/{id}")
  @Nested
  inner class GetReportByIdTest {

    @Nested
    inner class Security {

      @Test
      fun `access forbidden when no authority`() {
        webTestClient.get().uri("/report/1")
          .exchange()
          .expectStatus().isUnauthorized
      }

      @Test
      fun `access forbidden when no role`() {
        webTestClient.get().uri("/report/1")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf()))
          .exchange()
          .expectStatus().isForbidden
      }

      @Test
      fun `access forbidden with wrong role`() {
        webTestClient.get().uri("/report/1")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_PINEAPPLES")))
          .exchange()
          .expectStatus().isForbidden
      }
    }

    @Nested
    inner class FunctionalTests {
      @BeforeEach
      fun insertReport() {
        repo.save(buildReport(1, "GU1234A"))
      }

      @Test
      fun `can retrieve report by id`() {
        webTestClient.get().uri("/report/1")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_SAR_DATA_ACCESS")))
          .exchange()
          .expectStatus().isOk
          .expectBody().jsonPath("$.id").isEqualTo(1)
          .jsonPath("$.sequenceNo").isEqualTo(1)
          .jsonPath("$.incidentDate").isEqualTo("2024-01-01T14:00:00")
          .jsonPath("$.status").isEqualTo("IN_PROGRESS")
          .jsonPath("$.agencyId").isEqualTo("MDI")
          .jsonPath("$.userId").isEqualTo("user_id")
          .jsonPath("$.reporterName").isEqualTo("reporter_name")
          .jsonPath("$.offenderNo").isEqualTo("GU1234A")
          .jsonPath("$.bookingId").isEqualTo(1234)
          .jsonPath("$.formResponse").isEmpty
          .jsonPath("$.statements").isEmpty
      }

      @Test
      fun `requesting report that doesn't exist returns 404`() {
        webTestClient.get().uri("/report/2")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_SAR_DATA_ACCESS")))
          .exchange()
          .expectStatus().isNotFound
      }

      @Test
      fun `can retrieve report by id include statements`() {
        webTestClient.get().uri {
          it.path("/report/1")
            .queryParam("includeStatements", "true")
            .build()
        }
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_SAR_DATA_ACCESS")))
          .exchange()
          .expectStatus().isOk
          .expectBody().jsonPath("$.id").isEqualTo(1)
          .jsonPath("$.sequenceNo").isEqualTo(1)
          .jsonPath("$.incidentDate").isEqualTo("2024-01-01T14:00:00")
          .jsonPath("$.status").isEqualTo("IN_PROGRESS")
          .jsonPath("$.agencyId").isEqualTo("MDI")
          .jsonPath("$.userId").isEqualTo("user_id")
          .jsonPath("$.reporterName").isEqualTo("reporter_name")
          .jsonPath("$.offenderNo").isEqualTo("GU1234A")
          .jsonPath("$.bookingId").isEqualTo(1234)
          .jsonPath("$.formResponse").isEmpty
          .jsonPath("$.statements").isEmpty
      }
    }
  }

  @DisplayName("GET /prisoner/{id}/reports")
  @Nested
  inner class GetReportByOffenderNumberTest {

    @Nested
    inner class Security {

      @Test
      fun `access forbidden when no authority`() {
        webTestClient.get().uri("/prisoner/GU1234A/reports")
          .exchange()
          .expectStatus().isUnauthorized
      }

      @Test
      fun `access forbidden when no role`() {
        webTestClient.get().uri("/prisoner/GU1234A/reports")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf()))
          .exchange()
          .expectStatus().isForbidden
      }

      @Test
      fun `access forbidden with wrong role`() {
        webTestClient.get().uri("/prisoner/GU1234A/reports")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_PINEAPPLES")))
          .exchange()
          .expectStatus().isForbidden
      }
    }

    @Nested
    inner class FunctionalTests {
      @BeforeEach
      fun insertReport() {
        repo.save(buildReport(1, "GU1234A"))
        statementAmendmentRepository.save(statementAmendment)
        statementRepository.save(statement)
        repo.save(buildReportIncludeStatements(2, "GU1234B"))
      }

      @Test
      fun `can retrieve reports by prisoner number with report`() {
        val response: List<ReportSummary> = webTestClient.get().uri("/prisoner/GU1234A/reports")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_SAR_DATA_ACCESS")))
          .exchange()
          .expectStatus().isOk
          .expectBodyList(ReportSummary::class.java)
          .returnResult().responseBody!!

        assertThat(response.size).isEqualTo(1)

        val actualReport = response.first()
        assertThat(actualReport.id).isEqualTo(expectedReport.id)
        assertThat(actualReport.userId).isEqualTo(expectedReport.userId)
        assertThat(actualReport.sequenceNo).isEqualTo(expectedReport.sequenceNo)
        assertThat(actualReport.bookingId).isEqualTo(expectedReport.bookingId)
        assertThat(actualReport.createdDate).isCloseTo(expectedReport.createdDate, within(1, ChronoUnit.SECONDS))
        assertThat(actualReport.status).isEqualTo(expectedReport.status)
        assertThat(actualReport.submittedDate).isEqualTo(expectedReport.submittedDate)
        assertThat(actualReport.offenderNo).isEqualTo(expectedReport.offenderNo)
        assertThat(actualReport.reporterName).isEqualTo(expectedReport.reporterName)
        assertThat(actualReport.incidentDate).isEqualTo(expectedReport.incidentDate)
        assertThat(actualReport.agencyId).isEqualTo(expectedReport.agencyId)
        assertThat(actualReport.updatedDate).isCloseTo(expectedReport.updatedDate, within(1, ChronoUnit.SECONDS))
        assertThat(actualReport.deleted).isEqualTo(expectedReport.deleted)
      }

      @Test
      fun `can retrieve reports by prisoner number with report include Statements and include Form Response`() {
        val response: List<ReportSummary> =
          webTestClient.get().uri("/prisoner/GU1234B/reports?includeStatements=true&includeFormResponse=true")
            .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_SAR_DATA_ACCESS")))
            .exchange()
            .expectStatus().isOk
            .expectBodyList(ReportSummary::class.java)
            .returnResult().responseBody!!

        val actualReport = response.first()
        assertThat(response.size).isEqualTo(1)
        assertThat(actualReport.id).isEqualTo(expectedReportwithStatements.id)
      }

      @Test
      fun `searching by prisoner number with no reports`() {
        val response: List<ReportDetail> = webTestClient.get().uri("/prisoner/GU0000B/reports")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_SAR_DATA_ACCESS")))
          .exchange()
          .expectStatus().isOk
          .expectBodyList(ReportDetail::class.java)
          .returnResult().responseBody!!

        assertThat(response.size).isEqualTo(0)
      }
    }
  }
}
