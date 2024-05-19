package com.app.websocketschat.Domain.UseCases

import androidx.lifecycle.LiveData
import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Modules.Messages
import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// WebSocketUseCases.kt
data class WebSocketUseCases(
    val connect: ConnectUseCase,
    val sendMessage: SendMessageUseCase,
    val closeConnection: CloseConnectionUseCase,
    val getAllMessagesUseCase: GetAllMessagesUseCase
)

class ConnectUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke() {
        repository.connect()
    }
}

class SendMessageUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke(message: Messages) {
        repository.sendMessage(serializeMessage(message))
    }
    fun serializeMessage(message: Messages): String {
        return Gson().toJson(message)
    }
}

class CloseConnectionUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke() {
        repository.closeConnection()
    }
}
class GetAllMessagesUseCase @Inject constructor(private val repository: WebSocketRepository) {
     operator fun invoke(): Flow<List<EntityMessage>> {
        return repository.getAllMessages()
    }
}
