package uk.gov.justice.digital.hmpps.hmppsuofdataapi.resource

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.config.ReportNotFoundException
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto.Report
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.service.ReportService

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
  ): Report = reportService.getReport(id, includeStatements) ?: throw ReportNotFoundException(id.toString())

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
  ): List<Report> = reportService.getReportsByOffenderNumber(offenderNumber, includeStatements, includeFormResponse)
}
