package com.example.intellecta.network

import com.example.intellecta.network.syncModels.FileSyncRequest
import com.example.intellecta.network.syncModels.FileSyncResponse
import com.example.intellecta.network.syncModels.NoteSyncRequest
import com.example.intellecta.network.syncModels.NoteSyncResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService{

    @GET("api/")
    suspend fun healthCheck(): Response<Map<String, Any>>

    @GET("api/auth/test")
    suspend fun testAuth(): Response<Map<String, Any>>

    @GET("api/user/me")
    suspend fun getCurrentUser(): Response<Map<String, Any>>

    @GET("api/integration/test")
    suspend fun fullIntegrationTest(): Response<Map<String, Any>>

    @POST("api/notes")
    suspend fun syncNotes( @Body request: NoteSyncRequest ): Response<NoteSyncResponse>

    @POST("api/files")
    suspend fun  syncFiles( @Body request: FileSyncRequest) : Response<FileSyncResponse>

    @GET("api/sync/status")
    suspend fun getSyncStatus(): Response<Map<String, Any>>

}