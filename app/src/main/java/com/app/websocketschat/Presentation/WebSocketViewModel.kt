package com.app.websocketschat.Presentation

// WebSocketViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.websocketschat.Domain.Mapper.Mapper
import com.app.websocketschat.Domain.Modules.Messages
import com.app.websocketschat.Domain.UseCases.WebSocketUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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

}
