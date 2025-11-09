package com.example.intellecta.data

import android.content.Context
import android.util.Log
import androidx.browser.trusted.Token
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class TokenManager(context: Context){

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(token: String){
        sharedPreferences.edit().putString("jwt_token", token).apply()

    }

    suspend fun getTokenAsync(forceRefresh: Boolean = false): String? {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.w("TokenManager", "No Firebase user found in current context.")
            return if (!forceRefresh) getToken() else null
        }

        return try {
            val token = user.getIdToken(forceRefresh).await()?.token
            token?.let {
                saveToken(it)
            }
            token
        } catch (e: Exception) {
            Log.e("TokenManager", "Error getting/refreshing token", e)
            null
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString("jwt_token",null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove("jwt_token").apply()
    }

    fun hasToken(): Boolean {
        return getToken() != null
    }


}