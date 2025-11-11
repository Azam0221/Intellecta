package com.example.intellecta.supabase_config

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage



object SupabaseConfig {
    fun createClient(supabaseUrl: String, supabaseKey: String): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Storage)
        }
    }
}