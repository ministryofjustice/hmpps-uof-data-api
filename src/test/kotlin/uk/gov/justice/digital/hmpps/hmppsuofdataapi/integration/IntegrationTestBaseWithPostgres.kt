package uk.gov.justice.digital.hmpps.hmppsuofdataapi.integration

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

abstract class IntegrationTestBaseWithPostgres : IntegrationTestBase() {
  companion object {
    private val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:16.0"))
      .withDatabaseName("use-of-force")
      .withUsername("use-of-force")
      .withPassword("use-of-force")
      .withReuse(true)

    init {
      postgres.start()
    }

    @JvmStatic
    @DynamicPropertySource
    fun configureProperties(registry: DynamicPropertyRegistry) {
      registry.add("spring.datasource.url", postgres::getJdbcUrl)
      registry.add("spring.datasource.username", postgres::getUsername)
      registry.add("spring.datasource.password", postgres::getPassword)
    }
  }
}
