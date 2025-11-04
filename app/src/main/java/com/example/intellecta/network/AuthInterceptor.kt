package com.example.intellecta.network

import com.example.intellecta.data.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
):Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = runBlocking {
            tokenManager.getTokenAsync()
        }

        val request = if(token!=null){
            originalRequest.newBuilder()
                .addHeader("Authorization","Bearer $token")
                .build()

        }
        else{
            originalRequest
        }

        return chain.proceed(request)
    }
}