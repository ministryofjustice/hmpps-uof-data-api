package uk.gov.justice.digital.hmpps.hmppsuofdataapi.resource

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

@Schema(description = "Error response")
data class ErrorResponse(
  @Schema(description = "HTTP status code", example = "500", required = true)
  val status: Int,
  @Schema(description = "User message for the error", example = "No report found for ID `123`", required = true)
  val userMessage: String,
  @Schema(description = "More detailed error message", example = "[Details, sometimes a stack trace]", required = true)
  val developerMessage: String,
) {
  constructor(
    status: HttpStatus,
    userMessage: String,
    developerMessage: String? = null,
  ) :
    this(status.value(), userMessage, developerMessage ?: userMessage)
}
