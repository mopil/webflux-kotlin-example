package com.example.webflux_kotlin.webflux_kotlin.controller

import com.example.webflux_kotlin.webflux_kotlin.dto.ChatResponse
import com.example.webflux_kotlin.webflux_kotlin.dto.CreateChatRequest
import com.example.webflux_kotlin.webflux_kotlin.model.Chat
import com.example.webflux_kotlin.webflux_kotlin.repository.ChatRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chats")
class ChatController(
    private val chatRepository: ChatRepository
) {

    @GetMapping
    suspend fun getAllChats(): List<ChatResponse> {
        return chatRepository.findAll().asFlow().map { ChatResponse.from(it) }.toList()
    }

    @GetMapping("/{chatId}")
    suspend fun getChat(
        @PathVariable chatId: String
    ): ChatResponse {
        return chatRepository.findById(chatId).awaitLast().let {
            ChatResponse.from(it)
        }
    }

    @PostMapping
    suspend fun createChat(
        @RequestBody request: CreateChatRequest
    ): ChatResponse {
        return chatRepository.save(
            request.toEntity()
        ).awaitLast().let {
            ChatResponse.from(it)
        }
    }
}