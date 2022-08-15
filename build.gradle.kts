import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
    `maven-publish`
    kotlin("jvm")  version "1.6.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "br.com.softwareplace"
            artifactId = "json-logger"
            version = "1.0.0"

            from(components["java"])
        }
    }
}

group = "br.com.softwareplace"
version = "1.0.0"


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")

    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("io.mockk:mockk:1.12.5")
}

tasks.withType<Test> {
    description = "Runs unit tests"
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStackTraces = true
        exceptionFormat = FULL
        events = mutableSetOf(
            FAILED,
            SKIPPED
        )
    }
}
