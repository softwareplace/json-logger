package com.softwareplace.json.logger.log

import com.softwareplace.json.logger.log.*
import io.mockk.junit5.MockKExtension
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.event.Level
import java.time.LocalDate

@Suppress("KotlinPlaceholderCountMatchesArgumentCount")
@ExtendWith(MockKExtension::class)
class JsonLogTestExt {
    @Test
    fun `must to call logger with expected json`() {
        val logger = spyk(kLogger)

        JsonLog(logger)
            .add("test-key", "test-value")
            .level(Level.INFO)
            .run()

        verify {
            logger.run(Level.INFO, "{\"properties\":{\"test-key\":\"test-value\"}}")
            logger.info("{\"properties\":{\"test-key\":\"test-value\"}}", null)
        }
    }

    @Test
    fun `must to call log debug with expected json`() {
        val logger = spyk(kLogger)
        JsonLog(logger)
            .add("test-key", "test-value")
            .level(Level.DEBUG)
            .run()

        verify {
            logger.run(Level.DEBUG, "{\"properties\":{\"test-key\":\"test-value\"}}")
            logger.debug("{\"properties\":{\"test-key\":\"test-value\"}}", null)
        }
    }

    @Test
    fun `must to call log warn with expected json`() {
        val logger = spyk(kLogger)
        JsonLog(logger)
            .add("test-key", "test-value")
            .level(Level.WARN)
            .run()

        verify {
            logger.run(Level.WARN, "{\"properties\":{\"test-key\":\"test-value\"}}")
            logger.warn("{\"properties\":{\"test-key\":\"test-value\"}}", null)
        }
    }

    @Test
    fun `must to call logger with error message`() {
        val logger = spyk(kLogger)
        val error = IllegalArgumentException("test error log message")
        JsonLog(logger)
            .add("test-key", "test-value")
            .error(error)
            .level(Level.INFO)
            .printStackTracker(true)
            .run()

        verify {
            logger.run(Level.INFO, "{\"properties\":{\"test-key\":\"test-value\"},\"errorMessage\":\"test error log message\"}", error)
            logger.info("{\"properties\":{\"test-key\":\"test-value\"},\"errorMessage\":\"test error log message\"}", error)
        }
    }

    @Test
    fun `must to call logInfo with message`() {
        val logger = spyk(kLogger)
        JsonLog(logger)
            .add("test-key", "test-value")
            .message("this is a test log message created in {} of lib version {}", LocalDate.of(2022, 8, 12), "1.0.0")
            .level(Level.INFO)
            .run()

        verify {
            logger.run(
                Level.INFO,
                "{\"message\":\"this is a test log message created in 2022-08-12 of lib version 1.0.0\",\"properties\":{\"test-key\":\"test-value\"}}"
            )
            logger.info(
                "{\"message\":\"this is a test log message created in 2022-08-12 of lib version 1.0.0\",\"properties\":{\"test-key\":\"test-value\"}}",
                null
            )
        }
    }

    @Test
    fun `must to call log error with message`() {
        val logger = spyk(kLogger)
        val error = IllegalArgumentException("test error log message")
        JsonLog(logger)
            .add("test-key", "test-value")
            .error(error)
            .message("this is a test log message created in {} of lib version {}", LocalDate.of(2022, 8, 12), "1.0.0")
            .level(Level.ERROR)
            .printStackTracker(true)
            .run()

        verify {
            logger.run(
                Level.ERROR,
                "{\"message\":\"this is a test log message created in 2022-08-12 of lib version 1.0.0\",\"properties\":{\"test-key\":\"test-value\"},\"errorMessage\":\"test error log message\"}",
                error
            )
            logger.error(
                "{\"message\":\"this is a test log message created in 2022-08-12 of lib version 1.0.0\",\"properties\":{\"test-key\":\"test-value\"},\"errorMessage\":\"test error log message\"}",
                error
            )
        }
    }
}
