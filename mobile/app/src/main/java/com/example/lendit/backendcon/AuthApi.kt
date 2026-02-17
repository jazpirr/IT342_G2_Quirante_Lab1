package com.example.lendit.backendcon

import com.example.lendit.models.AuthResponse
import com.example.lendit.models.LoginRequest
import com.example.lendit.models.RegisterRequest
import com.example.lendit.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<UserResponse>

    @GET("api/user/me")
    fun getCurrentUser(
        @Header("Authorization") token: String
    ): Call<UserResponse>
}