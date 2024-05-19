package com.app.websocketschat.Presentation

// WebSocketViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.app.websocketschat.Domain.UseCases.WebSocketUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WebSocketViewModel @Inject constructor( val webSocketUseCases: WebSocketUseCases) : ViewModel() {

    private val _messages = MutableLiveData<String>()
    val messages: LiveData<String> get() = _messages

    fun connect() {
        viewModelScope.launch {
            webSocketUseCases.connect()
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            webSocketUseCases.sendMessage(message)
        }
    }

    fun closeConnection() {
        viewModelScope.launch {
            webSocketUseCases.closeConnection()
        }
    }

    fun onMessageReceived(message: String) {
        _messages.postValue(message)
    }
}
