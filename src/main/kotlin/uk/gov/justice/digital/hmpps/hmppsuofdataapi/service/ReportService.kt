package uk.gov.justice.digital.hmpps.hmppsuofdataapi.service

import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Report
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportDetail
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportSummaryRepository
import uk.gov.justice.hmpps.kotlin.sar.HmppsPrisonSubjectAccessRequestReactiveService
import uk.gov.justice.hmpps.kotlin.sar.HmppsSubjectAccessRequestContent
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class ReportService(
  private val reportRepository: ReportRepository,
  private val reportSummaryRepository: ReportSummaryRepository,
) : HmppsPrisonSubjectAccessRequestReactiveService {
  fun getReport(reportId: Long, includeStatements: Boolean): Report? = reportRepository.findById(reportId).getOrNull()?.toDto(includeStatements, true)

  fun getReportsByOffenderNumber(offenderNumber: String, includeStatements: Boolean, includeFormResponse: Boolean): List<Report> = if (!includeStatements && !includeFormResponse) {
    reportSummaryRepository.findAllByOffenderNo(offenderNumber).map { it.toDto(false, false) }
  } else {
    getReportDetailByOffenderNumber(offenderNumber).map { it.toDto(includeStatements, includeFormResponse) }
  }

  fun getReportDetailByOffenderNumber(offenderNumber: String): List<ReportDetail> = reportRepository.findAllByOffenderNo(offenderNumber)

  override suspend fun getPrisonContentFor(prn: String, fromDate: LocalDate?, toDate: LocalDate?): HmppsSubjectAccessRequestContent? = wrapInSubjectAccessFormat(prn, getReportsForSubjectAccess(prn, fromDate, toDate))

  suspend fun getReportsForSubjectAccess(prn: String, fromDate: LocalDate?, toDate: LocalDate?): List<ReportDetail> = coroutineScope {
    if (fromDate != null && toDate != null) {
      return@coroutineScope getReportsByOffenderNumberAndDateWindow(prn, fromDate, toDate)
    }
    if (fromDate != null) {
      return@coroutineScope reportRepository.findAllByOffenderNoAndIncidentDateAfter(prn, fromDate.atStartOfDay())
    }
    if (toDate != null) {
      return@coroutineScope reportRepository.findAllByOffenderNoAndIncidentDateBefore(prn, toDate.plusDays(1).atStartOfDay())
    }
    return@coroutineScope getReportDetailByOffenderNumber(prn)
  }

  fun getReportsByOffenderNumberAndDateWindow(offenderNumber: String, fromDate: LocalDate, toDate: LocalDate): List<ReportDetail> = reportRepository.findAllByOffenderNoAndIncidentDateBetween(offenderNumber, fromDate.atStartOfDay(), toDate.atStartOfDay())

  fun wrapInSubjectAccessFormat(offenderNumber: String, reports: List<ReportDetail>): HmppsSubjectAccessRequestContent? = if (reports.isEmpty()) {
    null
  } else {
    HmppsSubjectAccessRequestContent(
      content = reports.map {
        it.toDto(
          includeStatements = true,
          includeFormResponse = true,
        )
      },
    )
  }
}
