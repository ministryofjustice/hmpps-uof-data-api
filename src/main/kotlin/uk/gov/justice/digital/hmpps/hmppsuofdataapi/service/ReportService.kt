package uk.gov.justice.digital.hmpps.hmppsuofdataapi.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.config.NoDataException
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Report
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.SubjectAccessResponse
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportDetail
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportRepository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository.ReportSummaryRepository
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class ReportService(
  private val reportRepository: ReportRepository,
  private val reportSummaryRepository: ReportSummaryRepository,
) {
  fun getReport(reportId: Long, includeStatements: Boolean): Report? {
    return reportRepository.findById(reportId).getOrNull()?.toDto(includeStatements, true)
  }

  fun getReportsByOffenderNumber(offenderNumber: String, includeStatements: Boolean, includeFormResponse: Boolean): List<Report> {
    return if (!includeStatements && !includeFormResponse) {
      reportSummaryRepository.findAllByOffenderNo(offenderNumber).map { it.toDto(false, false) }
    } else {
      getReportDetailByOffenderNumber(offenderNumber).map { it.toDto(includeStatements, includeFormResponse) }
    }
  }

  fun getReportDetailByOffenderNumber(offenderNumber: String): List<ReportDetail> {
    return reportRepository.findAllByOffenderNo(offenderNumber)
  }

  fun getReportsForSubjectAccess(offenderNumber: String, fromDate: LocalDate?, toDate: LocalDate?): SubjectAccessResponse {
    if (fromDate != null && toDate != null) {
      return wrapInSubjectAccessFormat(offenderNumber, getReportsByOffenderNumberAndDateWindow(offenderNumber, fromDate, toDate))
    }
    if (fromDate != null) {
      return wrapInSubjectAccessFormat(offenderNumber, reportRepository.findAllByOffenderNoAndIncidentDateAfter(offenderNumber, fromDate.atStartOfDay()))
    }
    if (toDate != null) {
      return wrapInSubjectAccessFormat(offenderNumber, reportRepository.findAllByOffenderNoAndIncidentDateBefore(offenderNumber, toDate.plusDays(1).atStartOfDay()))
    }

    return wrapInSubjectAccessFormat(offenderNumber, getReportDetailByOffenderNumber(offenderNumber))
  }

  fun getReportsByOffenderNumberAndDateWindow(offenderNumber: String, fromDate: LocalDate, toDate: LocalDate): List<ReportDetail> {
    return reportRepository.findAllByOffenderNoAndIncidentDateBetween(offenderNumber, fromDate.atStartOfDay(), toDate.atStartOfDay())
  }

  fun wrapInSubjectAccessFormat(offenderNumber: String, reports: List<ReportDetail>): SubjectAccessResponse {
    if (reports.isEmpty()) {
      throw NoDataException(offenderNumber)
    } else {
      return SubjectAccessResponse(content = reports.map { it.toDto(true, true) })
    }
  }
}
