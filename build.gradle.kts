import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    kotlin("jvm") version "1.8.20"
}

repositories {
    mavenCentral()
    mavenLocal()
}

java {
    withJavadocJar()
    withSourcesJar()
}

val apiVersion = "0.0.7"


val appGroup = "com.softwareplace.jsonlogger"

group = appGroup

version = apiVersion

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = appGroup
            artifactId = "json-logger"
            version = apiVersion
            from(components["java"])
        }

        create<MavenPublication>("release") {
            from(components["java"])
            groupId = "com.github.eliasmeireles"
            artifactId = "json-logger"
            version = apiVersion
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")

    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.13.3")
    implementation("org.slf4j:jul-to-slf4j:1.7.30")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("io.mockk:mockk:1.13.2")
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


