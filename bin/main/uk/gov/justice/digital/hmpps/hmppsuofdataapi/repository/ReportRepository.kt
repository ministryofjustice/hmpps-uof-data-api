package uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportDetail
import java.time.LocalDateTime

@Repository
interface ReportRepository : JpaRepository<ReportDetail, Long> {
  fun findAllByOffenderNo(offenderNo: String): List<ReportDetail>

  fun findAllByOffenderNoAndIncidentDateBetween(offenderNo: String, dateFrom: LocalDateTime, dateTo: LocalDateTime): List<ReportDetail>
  fun findAllByOffenderNoAndIncidentDateAfter(offenderNo: String, dateFrom: LocalDateTime): List<ReportDetail>
  fun findAllByOffenderNoAndIncidentDateBefore(offenderNo: String, dateTo: LocalDateTime): List<ReportDetail>
}
