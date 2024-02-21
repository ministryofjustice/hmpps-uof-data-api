plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "5.15.2"
  kotlin("plugin.spring") version "1.9.22"
  kotlin("plugin.jpa") version "1.9.22"
}

configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

  implementation("com.squareup.moshi:moshi:1.15.1")
  implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
  implementation("com.squareup.moshi:moshi-adapters:1.15.1")

  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.20")
  testImplementation("io.jsonwebtoken:jjwt-impl:0.12.3")
  testImplementation("io.jsonwebtoken:jjwt-jackson:0.12.3")
  testImplementation("org.flywaydb:flyway-core")

  runtimeOnly("org.postgresql:postgresql:42.5.4")
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
