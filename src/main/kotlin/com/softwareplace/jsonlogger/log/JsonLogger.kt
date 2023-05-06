package com.softwareplace.jsonlogger.log

import com.fasterxml.jackson.databind.ObjectMapper
import com.softwareplace.jsonlogger.mapper.getObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level


inline val <reified T : Any> T.kLogger: Logger get() = LoggerFactory.getLogger(T::class.java)

val Logger.jsonLog: JsonLog get() = JsonLog(this)


private data class LoggerModel(
    val message: String?,
    val properties: Map<String, Any>?,
    val errorMessage: String? = null,
)

data class JsonLog(
    val kLog: Logger
) {
    private var message: String? = null
    private var properties: HashMap<String, Any>? = null
    private var error: Throwable? = null
    private var printStackTraceEnable: Boolean = false
    private var level: Level = Level.TRACE

    fun level(level: Level): JsonLog {
        this.level = level
        return this
    }

    fun error(error: Throwable?): JsonLog {
        this.error = error
        return this
    }

    fun printStackTrackerEnable(): JsonLog {
        this.printStackTraceEnable = true
        return this
    }

    fun printStackTrackerDisable(): JsonLog {
        this.printStackTraceEnable = false
        return this
    }

    fun message(message: String?, vararg args: Any?): JsonLog {
        message?.let {
            this.message = String.format(message.replace("{}", "%s"), *args)
        }
        return this
    }

    fun add(key: String?, value: Any?): JsonLog {
        key?.let {
            value?.let {
                if (properties == null) {
                    properties = HashMap()
                }

                properties?.let { it[key] = value }
            }
        }
        return this
    }

    fun run(
        mapper: ObjectMapper = getObjectMapper()
    ): Logger {
        val loggerMessage = mapper.writeValueAsString(
            LoggerModel(
                message = message,
                properties = properties,
                errorMessage = error?.message
            )
        )

        return if (printStackTraceEnable) {
            kLog.run(level, loggerMessage, error)
        } else {
            kLog.run(level, loggerMessage)
        }
    }

    fun run(): Logger {
        val loggerMessage = getObjectMapper().writeValueAsString(
            LoggerModel(
                message = message,
                properties = properties,
                errorMessage = error?.message
            )
        )
        return if (printStackTraceEnable) {
            kLog.run(level, loggerMessage, error)
        } else {
            kLog.run(level, loggerMessage)
        }
    }
}

fun Logger.run(
    level: Level = Level.INFO,
    message: String,
    error: Throwable? = null
): Logger {
    when (level) {
        Level.DEBUG -> debug(message, error)
        Level.TRACE -> trace(message, error)
        Level.WARN -> warn(message, error)
        Level.ERROR -> error(message, error)
        else -> info(message, error)
    }
    return this
}

