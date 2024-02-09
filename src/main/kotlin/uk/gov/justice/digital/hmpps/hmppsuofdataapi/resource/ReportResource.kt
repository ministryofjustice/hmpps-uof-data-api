package uk.gov.justice.digital.hmpps.hmppsuofdataapi.resource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.config.ReportNotFoundException
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.config.UnsupportedIdentifierException
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Report
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.SubjectAccessResponse
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.service.ReportService
import java.time.LocalDate

@RestController
@RequestMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(
  name = "Reports",
  description = "Returns Use of Force Reports",
)
class ReportResource(
  private val reportService: ReportService,
) {
  @GetMapping("report/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_SAR_DATA_ACCESS')")
  @Operation(
    summary = "Returns report and optionally the associated statements for this ID",
    description = "Requires role SAR_DATA_ACCESS",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "Returns report",
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Missing required role. Requires a role TBD",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "404",
        description = "Data not found",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun getReport(
    @Schema(description = "The report Id", example = "659", required = true)
    @PathVariable
    id: Long,
    @RequestParam(name = "includeStatements", required = false, defaultValue = "false") includeStatements: Boolean = false,
  ): Report {
    return reportService.getReport(id, includeStatements) ?: throw ReportNotFoundException(id.toString())
  }

  @GetMapping("/prisoner/{offenderNumber}/reports")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_SAR_DATA_ACCESS')")
  @Operation(
    summary = "Returns report and optionally the associated statements for this ID",
    description = "Requires role SAR_DATA_ACCESS",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "Returns report",
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Missing required role. Requires a role TBD",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "404",
        description = "Data not found",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun getReportsByOffender(
    @Schema(description = "The offender number (NOMIS prison number)", example = "G8133UA", required = true)
    @PathVariable
    offenderNumber: String,
    @RequestParam(name = "includeStatements", required = false, defaultValue = "false") includeStatements: Boolean = false,
    @RequestParam(name = "includeFormResponse", required = false, defaultValue = "false") includeFormResponse: Boolean = false,
  ): List<Report> {
    return reportService.getReportsByOffenderNumber(offenderNumber, includeStatements, includeFormResponse)
  }

  @GetMapping("/subject-access-request")
  @ResponseStatus(HttpStatus.OK)
  @Validated
  @PreAuthorize("hasRole('ROLE_SAR_DATA_ACCESS')")
  @Operation(
    summary = "Returns report and optionally the associated statements for this ID",
    description = "Requires role SAR_DATA_ACCESS",
    responses = [
      ApiResponse(
        responseCode = "200",
        description = "Request successfully processed - content found",
      ),
      ApiResponse(
        responseCode = "204",
        description = "Request successfully processed - no content found",
      ),
      ApiResponse(
        responseCode = "209",
        description = "Subject Identifier is not recognised by this service",
      ),
      ApiResponse(
        responseCode = "400",
        description = "The request was not formed correctly",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "401",
        description = "Unauthorized to access this endpoint",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
      ApiResponse(
        responseCode = "403",
        description = "Missing required role. Requires a role TBD",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))],
      ),
    ],
  )
  fun handleSubjectAccessRequest(
    @Schema(description = "NOMIS Prison Reference Number", example = "G8133UA", required = true)
    @RequestParam(name = "prn", required = true)
    prn: String,
    @Schema(description = "nDelius Case Reference Number", example = "1234", required = false)
    @RequestParam(name = "crn", required = false)
    crn: String?,
    @Schema(description = "Optional parameter denoting minimum date of event occurrence which should be returned in the response", example = "G8133UA", required = false)
    @RequestParam(name = "fromDate", required = false)
    fromDate: LocalDate?,
    @Schema(description = "Optional parameter denoting maximum date of event occurrence which should be returned in the response", example = "G8133UA", required = false)
    @RequestParam(name = "toDate", required = false)
    toDate: LocalDate?,
  ): SubjectAccessResponse {
    crn?.run { throw UnsupportedIdentifierException() }
    return reportService.getReportsForSubjectAccess(prn, fromDate, toDate)
  }
}
