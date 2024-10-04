package com.example.webflux_kotlin.webflux_kotlin.configuration

import org.springframework.http.HttpStatus

class ApiException(
    val exception: ExceptionMessage
) : RuntimeException()

enum class ExceptionMessage(
    val statusCode: HttpStatus,
    val message: String
) {
    TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "타입이 잘못되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터를 못 찾았습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러입니다.")
}

class ErrorResponse(
    val errorType: String,
    val statusCode: Int,
    val message: String?
) {
    companion object {
        fun from(exceptionMessage: ExceptionMessage) =
            ErrorResponse(exceptionMessage.name, exceptionMessage.statusCode.value(), exceptionMessage.message)
    }
}