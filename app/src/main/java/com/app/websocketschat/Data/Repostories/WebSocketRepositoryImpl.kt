package com.app.websocketschat.Data.Repostories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.websocketschat.Data.Remote.WebSocketListener
import com.app.websocketschat.Data.Remote.WebSocketService
import com.app.websocketschat.Data.Room.DaoInterface
import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Models.Messages
import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WebSocketRepositoryImpl @Inject constructor(
    val webSocketService: WebSocketService,
    val daoInterface: DaoInterface
) : WebSocketRepository {
    private val _messages = MutableLiveData<Messages>()
    val messages: LiveData<Messages> get() = _messages

    override fun connect() {
        webSocketService.start(WebSocketListener(
            onMessageReceived = { message ->
                val me = message.split(",")
                val messageEntity = EntityMessage(
                    me.get(0),
                    me.get(1),
                    me.get(2),
                    me.get(3)
                )
                // Save message to Room
                daoInterface.insertMessage(messageEntity)
                // Update liveData with new message
                _messages.postValue(
                    Messages(
                        me.get(0),
                        me.get(1),
                        me.get(2),
                        me.get(3)
                    )
                )
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

    override fun sendMessage(message: Messages) {
        webSocketService.send(message)
    }

    override fun closeConnection() {
        webSocketService.close()
    }

    override fun receivedData(): LiveData<Messages> = messages
    override fun getAllMessages(): Flow<List<EntityMessage>> {
       return daoInterface.getAllMessages()
    }

}