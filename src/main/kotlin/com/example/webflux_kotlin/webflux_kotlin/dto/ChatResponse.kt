package com.example.webflux_kotlin.webflux_kotlin.dto

import com.example.webflux_kotlin.webflux_kotlin.model.Chat
import java.time.LocalDateTime

data class ChatResponse(
    val id: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(chat: Chat) = ChatResponse(
            id = chat.id!!,
            content = chat.content,
            createdAt = chat.createdAt,
            updatedAt = chat.updatedAt
        )
    }
}