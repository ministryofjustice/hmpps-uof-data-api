package uk.gov.justice.digital.hmpps.hmppsuofdataapi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Use of Force Subject Access Report")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SubjectAccessResponse(
  @Schema(description = "The content found for the given identifier", example = "[]")
  val content: List<Report>,
)
