package com.app.websocketschat.Data.Remote

// WebSocketService.kt
import com.app.websocketschat.Domain.Models.MessageDataClass
import okhttp3.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WebSocketService @Inject constructor(private val url: String) {

    private var webSocket: WebSocket? = null
    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()
    private var listener: WebSocketListener? = null

    fun start(listener: WebSocketListener) {
        this.listener = listener
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(message: MessageDataClass) {
        val messageJson = message.message
        webSocket?.send(messageJson)
    }

    fun close() {
        webSocket?.close(1000, "Goodbye")
    }
}
