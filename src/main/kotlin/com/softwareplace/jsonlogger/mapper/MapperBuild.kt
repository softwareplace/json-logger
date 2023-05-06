package com.softwareplace.jsonlogger.mapper

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.softwareplace.jsonlogger.format.dateFormat

fun getObjectMapper(): ObjectMapper {
    return ObjectMapper()
        .setDateFormat(dateFormat)
        .registerModule(JavaTimeModule())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
}
