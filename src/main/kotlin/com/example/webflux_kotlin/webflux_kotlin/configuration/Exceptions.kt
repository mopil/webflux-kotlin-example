package com.example.webflux_kotlin.webflux_kotlin.configuration

import org.springframework.http.HttpStatus

class ApiException(
    val exception: ExceptionMessage
) : RuntimeException()

enum class ExceptionMessage(
    val statusCode: HttpStatus,
    val message: String = "요청을 처리하지 못 했습니다. 잠시 후 다시 시도해주세요."
) {
    INVALID_STATUS_OR_FIELD(HttpStatus.BAD_REQUEST, "잘못된 필드 혹은 상태입니다."),
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