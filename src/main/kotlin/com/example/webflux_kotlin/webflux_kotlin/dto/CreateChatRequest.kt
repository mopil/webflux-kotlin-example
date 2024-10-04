package com.example.webflux_kotlin.webflux_kotlin.dto

import com.example.webflux_kotlin.webflux_kotlin.model.Chat

data class CreateChatRequest(
    val content: String
) {
    fun toEntity() = Chat(
        content = content
    )
}