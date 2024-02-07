package uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.ReportSummary

@Repository
interface ReportSummaryRepository : JpaRepository<ReportSummary, Long> {
  fun findAllByOffenderNo(offenderNo: String): List<ReportSummary>
}
