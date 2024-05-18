package com.app.websocketschat.Domain.Repostories

import com.app.websocketschat.Domain.Models.MessageDataClass
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun connect()
    fun sendMessage(chatMessage: MessageDataClass)
    fun closeConnection()
    fun getAllMessages():Flow<List<MessageDataClass>>
}