package com.example.webfluxkotlin.webfluxkotlin.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Table

@Table("`user`")
class User(
    @Id
    @Column(name = "id")
    val id: Long = 0L,
    @Column(name = "nickname")
    val nickname: String,
    @Column(name = "password")
    val password: String,
)
