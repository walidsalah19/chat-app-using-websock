package com.app.websocketschat.Data.Repostories

// ChatRepositoryImpl.kt
import com.app.websocketschat.Data.Remote.ChatWebSocketListener
import com.app.websocketschat.Data.Remote.WebSocketService
import com.app.websocketschat.Data.Room.DaoInterface
import com.app.websocketschat.Domain.Models.MessageDataClass
import com.app.websocketschat.Domain.Repostories.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val webSocketService: WebSocketService,
    private val chatMessageDao: DaoInterface
) : ChatRepository {

    private val _messages = MutableStateFlow<List<MessageDataClass>>(emptyList())

    override fun connect() {
        webSocketService.start(ChatWebSocketListener(
            onMessageReceived = { chatMessage ->
                val chatMessageEntity = MessageDataClass(
                    chatMessage.id,
                    chatMessage.message,
                    chatMessage.senderId,
                    chatMessage.receiverId,
                    chatMessage.timestamp)
                // Save message to Room
                chatMessageDao.insertMessage(chatMessageEntity)
                // Update flow with new message
                val updatedMessages =
                    _messages.value.toMutableList().apply { add(chatMessageEntity) }
                _messages.value = updatedMessages
            },
            onOpen = {
                // Handle connection opened
            },
            onClose = {
                // Handle connection closed
            },
            onFailure = { throwable ->
                // Handle connection failure
            }
        ))
    }

    override fun sendMessage(chatMessage: MessageDataClass) {
        webSocketService.sendMessage(chatMessage)
        val chatMessageEntity = MessageDataClass(
            chatMessage.id,
            chatMessage.message,
            chatMessage.senderId,
            chatMessage.receiverId,
            chatMessage.timestamp
        )
        // Save message to Room
        chatMessageDao.insertMessage(chatMessageEntity)
        // Update flow with new message
        val updatedMessages = _messages.value.toMutableList().apply { add(chatMessageEntity) }
        _messages.value = updatedMessages
    }

    override fun closeConnection() {
        webSocketService.close()
    }

    override fun getAllMessages(): Flow<List<MessageDataClass>> {
        return chatMessageDao.getAllMessages()
    }
}
