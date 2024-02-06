package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.resource

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.IntegrationTestBase
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportDetail
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportSummary
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportRepository
import java.time.LocalDateTime

class ReportResourceIT : IntegrationTestBase() {

  @Autowired
  lateinit var repo: ReportRepository

  @BeforeEach
  fun setup() {
    repo.deleteAll()
  }

  private fun buildReport(
    id: Long,
    offenderNumber: String,
  ): ReportDetail {
    return ReportDetail(
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
  }

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
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_USE_OF_FORCE_REVIEWER")))
          .exchange()
          .expectStatus().isOk
      }

      @Test
      fun `requesting report that doesn't exist returns 404`() {
        webTestClient.get().uri("/report/2")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_USE_OF_FORCE_REVIEWER")))
          .exchange()
          .expectStatus().isNotFound
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
      }

      @Test
      fun `can retrieve reports by prisoner number with report`() {
        val response: List<ReportSummary> = webTestClient.get().uri("/prisoner/GU1234A/reports")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_USE_OF_FORCE_REVIEWER")))
          .exchange()
          .expectStatus().isOk
          .expectBodyList(ReportSummary::class.java)
          .returnResult().responseBody!!

        assertThat(response.size).isEqualTo(1)
      }

      @Test
      fun `searching by prisoner number with no reports`() {
        val response: List<ReportDetail> = webTestClient.get().uri("/prisoner/GU0000B/reports")
          .headers(jwtAuthHelper.setAuthorisation(roles = listOf("ROLE_USE_OF_FORCE_REVIEWER")))
          .exchange()
          .expectStatus().isOk
          .expectBodyList(ReportDetail::class.java)
          .returnResult().responseBody!!

        assertThat(response.size).isEqualTo(0)
      }
    }
  }

  companion object {
    private val log = LoggerFactory.getLogger(this::class.java)

    @JvmStatic
    @BeforeAll
    @Sql(
      "classpath:database/report.sql",
      "classpath:database/statement.sql",
      "classpath:database/statement_amendment.sql",
    )
    fun ensureTables() {
      log.info("Ensuring tables are created")
    }
  }
}
