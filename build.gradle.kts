import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "10.0.3"
  kotlin("plugin.spring") version "2.3.10"
  kotlin("plugin.jpa") version "2.3.10"
  idea
}

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:2.0.0")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-webclient")
  implementation("org.springframework.security:spring-security-access")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")

  testImplementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter-test:2.0.0")
  testImplementation("uk.gov.justice.service.hmpps:hmpps-subject-access-request-test-support:2.0.0")
  testImplementation("org.springframework.boot:spring-boot-starter-webclient-test")
  testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
  testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
  testImplementation("org.wiremock:wiremock-standalone:3.13.2")
  testImplementation("com.pauldijou:jwt-core_2.11:5.0.0")
  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.37") {
    exclude(group = "io.swagger.core.v3")
  }
  testImplementation("org.flywaydb:flyway-database-postgresql")
  testImplementation("org.flywaydb:flyway-core")
  testImplementation("com.zaxxer:HikariCP:7.0.2")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
  testImplementation("org.mockito.kotlin:mockito-kotlin:6.2.3")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.testcontainers:testcontainers-postgresql:2.0.3")

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("com.zaxxer:HikariCP")
  testRuntimeOnly("org.flywaydb:flyway-database-postgresql")
  testRuntimeOnly("com.h2database:h2:2.4.240")
}

kotlin {
  jvmToolchain(25)
}

tasks {
  withType<KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
  }
}
