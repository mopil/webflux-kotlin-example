package com.example.webfluxkotlin.webfluxkotlin.api.controller

import com.example.webfluxkotlin.webfluxkotlin.api.dto.request.SignUpUserRequest
import com.example.webfluxkotlin.webfluxkotlin.api.dto.response.UserResponse
import com.example.webfluxkotlin.webfluxkotlin.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 관련")
@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {
    @Operation(summary = "회원가입", description = "유저를 등록한다.")
    @PostMapping("/signup")
    suspend fun signUp(
        @RequestBody request: SignUpUserRequest,
    ) {
        userService.signUp(
            nickname = request.nickname,
            rawPassword = request.password,
        )
    }

    @Operation(summary = "회원 조회")
    @GetMapping("/{id}")
    suspend fun getUser(
        @PathVariable id: Long,
    ): UserResponse {
        return userService.getUser(id).let {
            UserResponse(
                id = it.id,
                nickname = it.nickname,
            )
        }
    }
}
