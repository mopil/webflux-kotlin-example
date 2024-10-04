package com.example.webflux_kotlin.webflux_kotlin.repository

import com.example.webflux_kotlin.webflux_kotlin.model.Chat
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ChatRepository : ReactiveMongoRepository<Chat, String> {
}