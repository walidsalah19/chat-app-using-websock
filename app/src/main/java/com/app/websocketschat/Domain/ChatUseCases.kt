package com.app.websocketschat.Domain

import com.app.websocketschat.Domain.Models.MessageDataClass
import com.app.websocketschat.Domain.Repostories.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class ChatUseCases @Inject constructor(
    val connect: ConnectChatUseCase,
    val sendMessage: SendChatMessageUseCase,
    val closeConnection: CloseChatConnectionUseCase,
    val getAllMessages: GetAllMessagesUseCase
)

class ConnectChatUseCase @Inject constructor(private val repository: ChatRepository) {
    operator fun invoke() {
        repository.connect()
    }
}

class SendChatMessageUseCase @Inject constructor(private val repository: ChatRepository) {
    operator fun invoke(chatMessage: MessageDataClass) {
        repository.sendMessage(chatMessage)
    }
}

class CloseChatConnectionUseCase @Inject constructor(private val repository: ChatRepository) {
    operator fun invoke() {
        repository.closeConnection()
    }
}

class GetAllMessagesUseCase @Inject constructor(private val repository: ChatRepository) {
    operator fun invoke(): Flow<List<MessageDataClass>> {
        return repository.getAllMessages()
    }
}
