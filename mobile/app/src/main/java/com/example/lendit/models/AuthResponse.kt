package com.example.lendit.models

data class AuthResponse(
    val token: String,
    val user: UserResponse
)
