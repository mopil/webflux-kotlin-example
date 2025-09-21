package com.example.webfluxkotlin.webfluxkotlin.api.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class SignUpUserRequest(
    @field:Schema(description = "닉네임", example = "user1", required = true)
    val nickname: String,
    @field:Schema(description = "비밀번호", example = "1102", required = true)
    val password: String,
)
