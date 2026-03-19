package uk.gov.justice.digital.hmpps.hmppsuofdataapi.config

import org.flywaydb.core.Flyway
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@TestConfiguration
@Profile("test")
class TestDatabaseConfiguration {

  @Bean
  fun flyway(dataSource: DataSource): Flyway = Flyway
    .configure()
    .dataSource(dataSource)
    .locations("classpath:db/migration")
    .baselineOnMigrate(true)
    .load()
    .also { it.migrate() }
}
