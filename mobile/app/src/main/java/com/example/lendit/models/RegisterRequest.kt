package com.example.lendit.models

data class RegisterRequest(
    val fName: String,
    val lName: String,
    val email: String,
    val password: String
)