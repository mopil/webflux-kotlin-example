package com.example.webflux_kotlin.webflux_kotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("user")
data class User(
    @Id
    val id: String,

    val nickname: String
)