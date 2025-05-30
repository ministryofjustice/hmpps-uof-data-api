package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.service
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportDetail
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportSummaryRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.service.ReportService
import java.time.LocalDate
import java.time.LocalDateTime

class ReportServiceTest {

  @Mock
  private lateinit var reportRepository: ReportRepository
  private lateinit var reportSummaryRepository: ReportSummaryRepository

  @InjectMocks
  private lateinit var reportService: ReportService

  val offenderNumber = "GU1234A"
  val offenderNumberB = "GU1234B"

  @BeforeEach
  fun setUp() {
    reportRepository = mock(ReportRepository::class.java)
    reportSummaryRepository = mock(ReportSummaryRepository::class.java)
    reportService = ReportService(reportRepository, reportSummaryRepository)
  }

  private fun buildReport(
    id: Long,
    offenderNumber: String,
    bookingId: Long? = 1234,
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

  @Test
  fun `test getReportsByOffenderNumberAndDateWindow returns correct reports`() {
    val fromDate = LocalDate.of(2024, 9, 1)
    val toDate = LocalDate.of(2024, 9, 30)
    val report1 = buildReport(1, offenderNumber)
    val report2 = buildReport(2, offenderNumberB)

    whenever(
      reportRepository.findAllByOffenderNoAndIncidentDateBetween(
        offenderNumber,
        fromDate.atStartOfDay(),
        toDate.atStartOfDay(),
      ),
    ).thenReturn(listOf(report1, report2))

    val reports = reportService.getReportsByOffenderNumberAndDateWindow(offenderNumber, fromDate, toDate)

    assertEquals(2, reports.size)
    assertEquals(report1, reports[0])
    assertEquals(report2, reports[1])
  }
}
