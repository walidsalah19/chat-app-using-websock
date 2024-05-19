package com.app.websocketschat.Presentation

// WebSocketViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Mapper.Mapper
import com.app.websocketschat.Domain.Models.Messages
import com.app.websocketschat.Domain.UseCases.WebSocketUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WebSocketViewModel @Inject constructor( val webSocketUseCases: WebSocketUseCases) : ViewModel() {

    private val _messages = MutableStateFlow<List<Messages>>(emptyList())
    val messages: StateFlow<List<Messages>> = _messages

    init {
        viewModelScope.launch {
            webSocketUseCases.getAllMessagesUseCase().collect {
                _messages.value = Mapper.convertToMessages(it)
            }
        }

    }
    fun getAllMessages(): Flow<List<EntityMessage>> = webSocketUseCases.getAllMessagesUseCase.invoke()
    fun connect() {
        viewModelScope.launch {
            webSocketUseCases.connect()
        }
    }

    fun sendMessage(message: Messages) {
        viewModelScope.launch {
            webSocketUseCases.sendMessage(message)
        }
    }

    fun closeConnection() {
        viewModelScope.launch {
            webSocketUseCases.closeConnection()
        }
    }
    fun receivedData():LiveData<Messages> =webSocketUseCases.received.invoke()

}
