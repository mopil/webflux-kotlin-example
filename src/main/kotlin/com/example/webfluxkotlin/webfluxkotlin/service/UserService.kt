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
            val user =
                User(
                    nickname = nickname,
                    password = rawPassword,
                )

            // suspend 함수: DB insert 완료될 때까지 대기
            val saved = userRepository.save(user)

            // 저장이 완료된 후 로그
            logger.info { "Sign Up 완료 -> id:${saved.id}, nickname:${saved.nickname}" }

            return saved
        }

        override suspend fun getUser(id: Long): User {
            return userRepository.findById(id)
                ?: throw NoSuchElementException("Not Found User. id: $id")
        }
    }
}
