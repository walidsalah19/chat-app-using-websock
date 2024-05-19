package com.app.websocketschat.Data.Remote

// WebSocketService.kt
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketService(private val url: String) : WebSocketListener() {

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    fun start() {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, this)
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Goodbye")
    }

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        // Handle connection opened
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        // Handle incoming message
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // Handle incoming binary message
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
        // Handle connection closing
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        // Handle connection failure
    }
}
