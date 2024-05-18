package com.app.websocketschat.Data.Remote

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        // Connection opened
        println("WebSocket connection opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        // Text message received
        println("Received message: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // Binary message received
        println("Received bytes: $bytes")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        // Connection failure
        println("WebSocket connection failure: ${t.message}")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        // Connection closed
        println("WebSocket connection closed: $reason")
    }
}
