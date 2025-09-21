package com.example.webfluxkotlin.webfluxkotlin.api.dto.response

import com.example.webfluxkotlin.webfluxkotlin.configuration.ExceptionMessage

class ErrorResponse(
    val errorType: String,
    val statusCode: Int,
    val message: String?,
) {
    companion object {
        fun from(exceptionMessage: ExceptionMessage) =
            ErrorResponse(exceptionMessage.name, exceptionMessage.statusCode.value(), exceptionMessage.message)
    }
}
