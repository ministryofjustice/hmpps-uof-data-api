package uk.gov.justice.digital.hmpps.hmppsuofdataapi.utils

import org.springframework.stereotype.Component

@Component
object UserContext {
  private val authToken = ThreadLocal<String>()
  fun getAuthToken(): String? = authToken.get()
  fun setAuthToken(aToken: String?) = authToken.set(aToken)
}
