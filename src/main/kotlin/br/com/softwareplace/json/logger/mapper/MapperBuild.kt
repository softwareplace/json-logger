package br.com.softwareplace.json.logger.mapper

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import br.com.softwareplace.json.logger.format.dateFormat

fun getObjectMapper(): ObjectMapper {
    return ObjectMapper()
        .setDateFormat(dateFormat)
        .registerModule(JavaTimeModule())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
}
