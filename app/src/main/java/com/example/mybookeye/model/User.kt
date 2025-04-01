package com.example.mybookeye.model

import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val username: String,
    val passwordHash: String,
    val email: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
