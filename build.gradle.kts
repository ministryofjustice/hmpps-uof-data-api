import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "9.0.2"
  kotlin("plugin.spring") version "2.2.20"
  kotlin("plugin.jpa") version "2.2.20"
  id("org.jetbrains.kotlinx.kover") version "0.9.2"
}

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:1.7.0")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

  implementation("com.squareup.moshi:moshi:1.15.2")
  implementation("com.squareup.moshi:moshi-kotlin:1.15.2")
  implementation("com.squareup.moshi:moshi-adapters:1.15.2")

  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.34")
  testImplementation("io.jsonwebtoken:jjwt-impl:0.13.0")
  testImplementation("io.jsonwebtoken:jjwt-jackson:0.13.0")
  testImplementation("org.flywaydb:flyway-core")

  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")

  runtimeOnly("org.postgresql:postgresql:42.7.8")
  testRuntimeOnly("org.flywaydb:flyway-database-postgresql")
  testRuntimeOnly("com.h2database:h2:2.3.232")
}

kotlin {
  jvmToolchain(21)
}

tasks {
  withType<KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
  }
}
