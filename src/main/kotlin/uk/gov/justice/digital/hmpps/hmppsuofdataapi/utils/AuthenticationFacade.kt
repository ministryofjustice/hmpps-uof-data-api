package uk.gov.justice.digital.hmpps.hmppsuofdataapi.utils

import org.apache.commons.lang3.RegExUtils
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import uk.gov.justice.digital.hmpps.hmppsuofdataapi.SYSTEM_USERNAME
import java.util.*
import java.util.stream.Collectors

@Component
class AuthenticationFacade {

  fun getUserOrSystemInContext() = currentUsername ?: SYSTEM_USERNAME

  val authentication: Authentication
    get() = SecurityContextHolder.getContext().authentication
  val currentUsername: String?
    get() {
      val username: String?
      val userPrincipal = userPrincipal
      username = when (userPrincipal) {
        is String -> {
          userPrincipal
        }
        is UserDetails -> {
          userPrincipal.username
        }
        is Map<*, *> -> {
          userPrincipal["username"] as String?
        }
        else -> {
          null
        }
      }
      return username
    }
  private val userPrincipal: Any?
    get() {
      val auth = authentication
      return auth.principal
    }

  companion object {
    fun hasRoles(vararg allowedRoles: String): Boolean {
      val roles = Arrays.stream(allowedRoles)
        .map { r: String? -> RegExUtils.replaceFirst(r, "ROLE_", "") }
        .collect(Collectors.toList())
      return hasMatchingRole(roles, SecurityContextHolder.getContext().authentication)
    }

    private fun hasMatchingRole(roles: List<String>, authentication: Authentication?): Boolean {
      return authentication != null &&
        authentication.authorities.stream()
          .anyMatch { a: GrantedAuthority? -> roles.contains(RegExUtils.replaceFirst(a!!.authority, "ROLE_", "")) }
    }
  }
}
