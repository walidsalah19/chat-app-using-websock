package com.app.websocketschat.Domain.Repostories

import com.app.websocketschat.Domain.Models.MessageDataClass

interface WebSocketRepository {
    fun connect()
    fun sendMessage(message: String)
    fun closeConnection()
}