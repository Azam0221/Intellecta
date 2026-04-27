package com.example.intellecta.supabase_config

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage



object SupabaseConfig {

    private const val TAG = "SupabaseConfig"

    fun createClient(supabaseUrl: String, supabaseKey: String): SupabaseClient {

        return try {
            createSupabaseClient(
                supabaseUrl = supabaseUrl,
                supabaseKey = supabaseKey
            ) {
                install(Storage)
                install(Auth)
                install(Postgrest)
                install(Functions)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating Supabase client: ${e.message}", e)
            throw e
        }
    }

}
