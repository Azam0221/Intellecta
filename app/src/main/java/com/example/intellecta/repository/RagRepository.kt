package com.example.intellecta.repository

import android.util.Log
import com.example.intellecta.model.response.RagResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class RagRepository(
    private val supabase: SupabaseClient
){
    companion object{
        private const val TAG = "RagRepository"

    }

    suspend fun indexNote(
        noteId: String,
        userId: String,
        title: String,
        content: String
    ){
        try {
            supabase.functions.invoke(
                function = "index-note",
                body = buildJsonObject {
                    put("note_id", noteId)
                    put("user_id", userId)
                    put("title", title)
                    put("content", content)
                }
            )
            Log.d(TAG,"Note indexed successfully: $title")
        }
        catch (e: Exception){
            Log.e(TAG,"Failed to index note: $title")
        }
    }

    suspend fun queryNotes(
        question: String,
        userId: String
    ) : RagResponse {
        return try {
            val result = supabase.functions.invoke(
                function = "query-notes",
                body = buildJsonObject {
                    put("question", question)
                    put("user_id", userId)
                }
            )

            val json =  kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            json.decodeFromString<RagResponse>(result.body())
        }
        catch (e: Exception){
            Log.e(TAG, "Failed to query notes", e)
            RagResponse(
                answer = "Something went wrong. Please try again.",
                sources = emptyList()
            )
        }
    }

}