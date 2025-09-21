package com.example.webfluxkotlin.webfluxkotlin.service

import com.example.webfluxkotlin.webfluxkotlin.domain.model.User
import com.example.webfluxkotlin.webfluxkotlin.domain.model.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserService {
    suspend fun signUp(
        nickname: String,
        rawPassword: String,
    ): User

    suspend fun getUser(id: Long): User

    @Service
    @Transactional(readOnly = true)
    class Default(
        private val userRepository: UserRepository,
    ) : UserService {
        private val logger = KotlinLogging.logger {}

        @Transactional
        override suspend fun signUp(
            nickname: String,
            rawPassword: String,
        ): User {
            User(
                nickname = nickname,
                password = rawPassword,
            ).let {
                logger.info { "Sign Up User id:${it.id} nickname: ${it.nickname}" }
                return userRepository.save(it)
            }
        }

        override suspend fun getUser(id: Long): User {
            return userRepository.findById(id)
                ?: throw IllegalArgumentException("Not Found User. id: $id")
        }
    }
}
