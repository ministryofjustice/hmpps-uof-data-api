package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration.service
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
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
  var fromDate = LocalDate.of(2024, 1, 1)
  var toDate = LocalDate.of(2024, 1, 9)

  private fun buildReport(
    id: Long,
    offenderNumber: String,
  ): ReportDetail {
    return ReportDetail(
      id, "{}",
      "user_id",
      1,
      1234,
      LocalDateTime.of(2024, 1, 10, 14, 0),
      "IN_PROGRESS",
      null,
      offenderNumber,
      "reporter_name",
      LocalDateTime.of(2024, 1, 10, 14, 0),
      "MDI",
      LocalDateTime.of(2024, 1, 10, 14, 0),
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

  @Test
  fun `test getReportsForSubjectAccess returns correct reports coroutines runTest with only fromDate `() = runTest {
    val gotListReportDetail = listOf(report1)
    val listReportDetail = listOf(report1)

    whenever(
      reportRepository.findAllByOffenderNoAndIncidentDateAfter(
        offenderNumber,
        fromDate.atStartOfDay(),
      ),
    ).thenReturn(gotListReportDetail)

    val reports = reportService.getReportsForSubjectAccess(offenderNumber, fromDate, null)
    assertEquals(listReportDetail, gotListReportDetail)
    assertEquals(listReportDetail, reports)
    assert(reports.size == 1)
  }

  @Test
  fun `test getReportsForSubjectAccess returns correct reports coroutines runTest with only toDate `() = runTest {
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
      reportRepository.findAllByOffenderNoAndIncidentDateBefore(
        offenderNumber,
        toDate.plusDays(1).atStartOfDay(),
      ),
    ).thenReturn(listOf(report1, report2))

    whenever(reportServiceMock.getReportsByOffenderNumberAndDateWindow(offenderNumber, fromDate, toDate)).thenReturn(
      listReportDetail,
    )
    val gotHmppsSubjectAccessRequestContent = reportService.getPrisonContentFor(offenderNumber, null, toDate)
    assertEquals(expectedHmppsSubjectAccessRequestContent, gotHmppsSubjectAccessRequestContent)
  }

  @Test
  fun `test getReportsForSubjectAccess returns correct reports coroutines runTest without fromDate and toDate `() = runTest {
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
      reportRepository.findAllByOffenderNo(
        offenderNumber,
      ),
    ).thenReturn(listOf(report1, report2))

    whenever(reportServiceMock.getReportDetailByOffenderNumber(offenderNumber)).thenReturn(listReportDetail)
    val gotHmppsSubjectAccessRequestContent = reportService.getPrisonContentFor(offenderNumber, null, null)
    assertEquals(expectedHmppsSubjectAccessRequestContent, gotHmppsSubjectAccessRequestContent)
  }

  @Test
  fun `test getReportsForSubjectAccess returns null when reports are empty `() = runTest {
    var listReportDetailVoid: List<ReportDetail> = emptyList()

    whenever(
      reportRepository.findAllByOffenderNoAndIncidentDateBetween(
        offenderNumber,
        fromDate.atStartOfDay(),
        toDate.atStartOfDay(),
      ),
    ).thenReturn(listReportDetailVoid)

    whenever(reportServiceMock.getReportsByOffenderNumberAndDateWindow(offenderNumber, fromDate, toDate)).thenReturn(
      listReportDetailVoid,
    )

    val gotHmppsSubjectAccessRequestContent = reportService.getPrisonContentFor(offenderNumber, fromDate, toDate)
    assert(listReportDetailVoid.isEmpty())
    assertNull(gotHmppsSubjectAccessRequestContent)
  }
}
