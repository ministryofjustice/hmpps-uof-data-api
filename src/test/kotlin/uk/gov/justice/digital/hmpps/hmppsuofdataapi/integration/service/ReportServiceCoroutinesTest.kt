package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.service
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportDetail
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportSummaryRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.service.ReportService
import uk.gov.justice.hmpps.kotlin.sar.HmppsSubjectAccessRequestContent
import java.time.LocalDate
import java.time.LocalDateTime

class ReportServiceCoroutinesTest {

  private lateinit var reportRepository: ReportRepository
  private lateinit var reportSummaryRepository: ReportSummaryRepository
  private lateinit var reportServiceMock: ReportService

  @InjectMocks
  private lateinit var reportService: ReportService

  @BeforeEach
  fun setUp() {
    reportRepository = mock(ReportRepository::class.java)
    reportSummaryRepository = mock(ReportSummaryRepository::class.java)

    reportService = ReportService(reportRepository, reportSummaryRepository)
    reportServiceMock = mock(ReportService::class.java)
  }

  val offenderNumber = "G1234A"
  val offenderNumberB = "G1234AB"

  val report1 = buildReport(1, offenderNumber)
  val report2 = buildReport(2, offenderNumberB)
  val fromDate = LocalDate.of(2024, 9, 1)
  val toDate = LocalDate.of(2024, 9, 30)

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

  @Test
  fun `test getPrisonContentFor returns correct reports coroutines runTest`() = runTest {
    val listReportDetail = listOf(report1, report2)

    val expectedHmppsSubjectAccessRequestContent = HmppsSubjectAccessRequestContent(
      content = listOf(report1, report2).map {
        it.toDto(
          includeStatements = true,
          includeFormResponse = true,
        )
      },
    )

    whenever(
      reportRepository.findAllByOffenderNoAndIncidentDateBetween(
        offenderNumber,
        fromDate.atStartOfDay(),
        toDate.atStartOfDay(),
      ),
    ).thenReturn(listOf(report1, report2))

    whenever(reportServiceMock.getReportsByOffenderNumberAndDateWindow(offenderNumber, fromDate, toDate)).thenReturn(listReportDetail)
    whenever(reportServiceMock.getPrisonContentFor(offenderNumber, fromDate, toDate)).thenReturn(expectedHmppsSubjectAccessRequestContent)

    val gotHmppsSubjectAccessRequestContent = reportService.getPrisonContentFor(offenderNumber, fromDate, toDate)

    assertEquals(gotHmppsSubjectAccessRequestContent, expectedHmppsSubjectAccessRequestContent)
  }
}
