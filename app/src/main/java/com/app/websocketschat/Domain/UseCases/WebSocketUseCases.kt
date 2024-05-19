package com.app.websocketschat.Domain.UseCases

import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import javax.inject.Inject

// WebSocketUseCases.kt
data class WebSocketUseCases(
    val connect: ConnectUseCase,
    val sendMessage: SendMessageUseCase,
    val closeConnection: CloseConnectionUseCase
)

class ConnectUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke() {
        repository.connect()
    }
}

class SendMessageUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke(message: String) {
        repository.sendMessage(message)
    }
}

class CloseConnectionUseCase @Inject constructor(private val repository: WebSocketRepository) {
    operator fun invoke() {
        repository.closeConnection()
    }
}
