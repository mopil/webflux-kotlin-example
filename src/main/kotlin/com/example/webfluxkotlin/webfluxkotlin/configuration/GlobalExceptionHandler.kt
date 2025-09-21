package com.example.webfluxkotlin.webfluxkotlin.configuration

import com.example.webfluxkotlin.webfluxkotlin.api.dto.response.ErrorResponse
import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Component
@Order(-2) // Order(-1) 에 등록된 DefaultErrorWebExceptionHandler 보다 높은 우선순위를 부여하기 위함.
class GlobalExceptionHandler : WebExceptionHandler {
    private val logger = KotlinLogging.logger {}

    override fun handle(
        exchange: ServerWebExchange,
        ex: Throwable,
    ): Mono<Void> =
        handle(ex)
            .flatMap {
                it.writeTo(exchange, HandlerStrategiesResponseContext(HandlerStrategies.withDefaults()))
            }.flatMap {
                Mono.empty()
            }

    fun handle(throwable: Throwable): Mono<ServerResponse> {
        logger.warn { "GlobalExceptionHandler: ${throwable.message}" }
        return when (throwable) {
            is ApiException -> {
                createResponse(throwable.exception)
            }

            is IllegalStateException, is IllegalArgumentException -> {
                ServerResponse
                    .status(HttpStatus.BAD_REQUEST)
                    .bodyValue(
                        ErrorResponse(
                            errorType = ExceptionMessage.INVALID_STATUS_OR_FIELD.name,
                            statusCode = HttpStatus.BAD_REQUEST.value(),
                            message = throwable.message ?: ExceptionMessage.INVALID_STATUS_OR_FIELD.message,
                        ),
                    )
            }

            is NoSuchElementException -> {
                createResponse(ExceptionMessage.NOT_FOUND)
            }

            else -> {
                logger.error(throwable.message)
                createResponse(ExceptionMessage.INTERNAL_SERVER_ERROR)
            }
        }
    }

    private fun createResponse(exception: ExceptionMessage): Mono<ServerResponse> {
        return ServerResponse
            .status(exception.statusCode)
            .bodyValue(ErrorResponse.from(exception))
    }
}

private class HandlerStrategiesResponseContext(val strategies: HandlerStrategies) : ServerResponse.Context {
    override fun messageWriters(): List<HttpMessageWriter<*>> {
        return this.strategies.messageWriters()
    }

    override fun viewResolvers(): List<ViewResolver> {
        return this.strategies.viewResolvers()
    }
}
