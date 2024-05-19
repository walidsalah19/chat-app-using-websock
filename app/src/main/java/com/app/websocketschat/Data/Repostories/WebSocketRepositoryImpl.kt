package com.app.websocketschat.Data.Repostories

import com.app.websocketschat.Data.Remote.WebSocketService
import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import javax.inject.Inject

class WebSocketRepositoryImpl @Inject constructor(val webSocketService: WebSocketService) : WebSocketRepository {

    override fun connect() {
        webSocketService.start()
    }

    override fun sendMessage(message: String) {
        webSocketService.send(message)
    }

    override fun closeConnection() {
        webSocketService.close()
    }
}