package com.app.websocketschat.Domain.Repostories

import androidx.lifecycle.LiveData
import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Models.Messages
import kotlinx.coroutines.flow.Flow

interface WebSocketRepository {
    fun connect()
    fun sendMessage(message: Messages)
    fun closeConnection()
    fun receivedData():LiveData<Messages>
    fun getAllMessages(): Flow<List<EntityMessage>>
}