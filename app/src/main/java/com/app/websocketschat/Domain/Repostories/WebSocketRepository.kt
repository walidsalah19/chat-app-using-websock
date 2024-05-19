package com.app.websocketschat.Domain.Repostories

import androidx.lifecycle.LiveData
import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Modules.Messages
import kotlinx.coroutines.flow.Flow

interface WebSocketRepository {
    fun connect()
    fun sendMessage(message: String)
    fun closeConnection()
    fun getAllMessages(): Flow<List<EntityMessage>>
}