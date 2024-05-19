package com.app.websocketschat.Domain.UseCases

import androidx.lifecycle.LiveData
import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Models.Messages
import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// WebSocketUseCases.kt
data class WebSocketUseCases(
    val connect: ConnectUseCase,
    val sendMessage: SendMessageUseCase,
    val closeConnection: CloseConnectionUseCase,
    val received:ReceivedUseCase,
    val getAllMessagesUseCase: GetAllMessagesUseCase
)

class ConnectUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke() {
        repository.connect()
    }
}

class SendMessageUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke(message: Messages) {
        repository.sendMessage(message)
    }
}

class CloseConnectionUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke() {
        repository.closeConnection()
    }
}
class ReceivedUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke(): LiveData<Messages> {
       return repository.receivedData()
    }
}
class GetAllMessagesUseCase @Inject constructor(private val repository: WebSocketRepository) {
     operator fun invoke(): Flow<List<EntityMessage>> {
        return repository.getAllMessages()
    }
}
