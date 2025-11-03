package com.example.intellecta.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService{

    @GET("api/")
    suspend fun healthCheck(): Response<Map<String, Any>>

    @GET("api/auth/test")
    suspend fun testAuth(): Response<Map<String, Any>>

    @GET("api/user/me")
    suspend fun getCurrentUser(): Response<Map<String, Any>>

    @GET("api/integration/test")
    suspend fun fullIntegrationTest(): Response<Map<String, Any>>

}