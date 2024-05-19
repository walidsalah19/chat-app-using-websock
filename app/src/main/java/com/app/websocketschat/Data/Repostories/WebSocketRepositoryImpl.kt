package com.app.websocketschat.Data.Repostories

import com.app.websocketschat.Data.Remote.WebSocketListener
import com.app.websocketschat.Data.Remote.WebSocketService
import com.app.websocketschat.Data.Room.DaoInterface
import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Modules.Messages
import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WebSocketRepositoryImpl @Inject constructor(
    val webSocketService: WebSocketService,
    val daoInterface: DaoInterface
) : WebSocketRepository {
    override fun connect() {
        webSocketService.start(WebSocketListener(
            onMessageReceived = { message ->
                val me = deserializeMessage(message)
                val messageEntity = me?.let {
                    EntityMessage(
                        it.message,
                        me.senderId,
                        me.receiverId,
                        me.timestamp
                    )

                }
                // Save message to Room
                if (messageEntity != null) {
                    daoInterface.insertMessage(messageEntity)
                }
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

    fun deserializeMessage(jsonString: String): Messages? {
        val gson = Gson()
        return try {
            gson.fromJson(jsonString, Messages::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun sendMessage(message: Messages) {
        webSocketService.send(message)
    }

    override fun closeConnection() {
        webSocketService.close()
    }

    override fun getAllMessages(): Flow<List<EntityMessage>> {
        return daoInterface.getAllMessages()
    }

}