plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "5.15.6"
  kotlin("plugin.spring") version "1.9.23"
  kotlin("plugin.jpa") version "1.9.23"
  id("org.jetbrains.kotlinx.kover") version "0.8.2"
}

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:0.2.4")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

  implementation("com.squareup.moshi:moshi:1.15.1")
  implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
  implementation("com.squareup.moshi:moshi-adapters:1.15.1")

  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.22")
  testImplementation("io.jsonwebtoken:jjwt-impl:0.12.5")
  testImplementation("io.jsonwebtoken:jjwt-jackson:0.12.5")
  testImplementation("org.flywaydb:flyway-core")

  runtimeOnly("org.postgresql:postgresql:42.7.2")
  testRuntimeOnly("com.h2database:h2:2.2.220")
}

kotlin {
  jvmToolchain(21)
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "21"
    }
  }
}
