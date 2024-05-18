package com.app.websocketschat.Presentation

// ChatViewModel.kt
import androidx.lifecycle.*
import androidx.room.Insert
import com.app.websocketschat.Domain.ChatUseCases
import com.app.websocketschat.Domain.Models.MessageDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor
    (val chatUseCases: ChatUseCases)
    :ViewModel(){

    private val _messages = MutableLiveData<List<MessageDataClass>>()
    val messages: LiveData<List<MessageDataClass>> get() = _messages


    private fun getAllMessages() {
        viewModelScope.launch {
            chatUseCases.getAllMessages().collect { entities ->
                val chatMessages = entities.map { entity ->
                    MessageDataClass(entity.id, entity.message, entity.senderId,
                        entity.receiverId,entity.timestamp)
                }
                _messages.postValue(chatMessages)
            }
        }
    }

    fun connect() {
        viewModelScope.launch {
            chatUseCases.connect()
        }
    }

    fun sendMessage(sender: String, message: String) {
        val chatMessage =MessageDataClass(0, message,"senderId","receiverId", System.currentTimeMillis())
        viewModelScope.launch {
            chatUseCases.sendMessage(chatMessage)
        }
    }

    fun closeConnection() {
        viewModelScope.launch {
            chatUseCases.closeConnection()
        }
    }

    fun onMessageReceived(chatMessage: MessageDataClass) {
        val currentList = _messages.value?.toMutableList() ?: mutableListOf()
        currentList.add(chatMessage)
        _messages.postValue(currentList)
    }
}
