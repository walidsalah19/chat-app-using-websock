package com.app.websocketschat.Data.Remote
import com.app.websocketschat.Domain.Models.MessageDataClass
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class ChatWebSocketListener(
    private val onMessageReceived: (MessageDataClass) -> Unit,
    private val onOpen: () -> Unit,
    private val onClose: () -> Unit,
    private val onFailure: (Throwable) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        onOpen()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val chatMessage = MessageDataClass(0,text,"sender","receiver",0)
        onMessageReceived(chatMessage)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // Handle binary messages if necessary
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
        onClose()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        onFailure(t)
    }
}
