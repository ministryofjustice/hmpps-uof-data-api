package uk.gov.justice.digital.hmpps.hmppsuofdataapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.model.StatementAmendment

@Repository
interface StatementAmendmentRepository : JpaRepository<StatementAmendment, Long>
