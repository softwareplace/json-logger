package json.logger.log

import com.fasterxml.jackson.databind.ObjectMapper
import json.logger.mapper.getObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level


val Any.log: Logger get() = LoggerFactory.getLogger(this::class.java)


private data class LoggerModel(
    val message: String?,
    val properties: Map<String, Any>?,
    val errorMessage: String? = null,
)

data class JsonLog(private val logger: Logger) {
    private var message: String? = null
    private var properties: HashMap<String, Any>? = null
    private var error: Throwable? = null


    fun error(error: Throwable?): JsonLog {
        this.error = error
        return this
    }

    fun message(message: String, vararg args: Any?): JsonLog {
        this.message = String.format(message.replace("{}", "%s"), *args)
        return this
    }

    fun add(key: String, value: Any): JsonLog {
        if (properties == null) {
            properties = HashMap()
        }
        properties?.let { it[key] = value }
        return this
    }

    fun run(
        level: Level,
        mapper: ObjectMapper = getObjectMapper()
    ) {
        val loggerMessage = mapper.writeValueAsString(
            LoggerModel(
                message = message,
                properties = properties,
                errorMessage = error?.message
            )
        )
        logger.run(level, loggerMessage, error)
    }
}


fun Logger.run(
    level: Level = Level.INFO,
    message: String,
    error: Throwable? = null
) {
    when (level) {
        Level.DEBUG -> debug(message, error)
        Level.TRACE -> trace(message, error)
        Level.WARN -> warn(message, error)
        Level.ERROR -> error(message, error)
        else -> info(message, error)
    }
}

