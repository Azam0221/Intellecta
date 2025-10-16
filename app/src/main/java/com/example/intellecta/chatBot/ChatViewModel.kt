package com.example.intellecta.chatBot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellecta.repository.NoteRepository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel: GenerativeModel = GenerativeModel(
        // Specify a Gemini model appropriate for your use case
        modelName = "gemini-2.5-pro",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = Constants.apiKey
    )


    fun sendMessage(question: String) {

        viewModelScope.launch {
            try {

                val localData = noteRepository.getAllNotes()
                val contextText = localData.joinToString("\n") { "Title: ${it.title}, Content: ${it.content}" }


                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    } + content("user") {
                        text("Use the following local data to answer questions and don't tell user nothing just answer him what he is asking: \n$contextText")
                    }
                )


                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Thinking....","model"))

                val response = chat.sendMessage(question)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel(response.text.toString(), "model"))


            }
            catch (e: Exception) {
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel( "Error:"+e.message.toString(),"model"))
                Log.d("ChatViewmodel", "sendMessage: ${e.message} .")
            }
        }
    }
}
