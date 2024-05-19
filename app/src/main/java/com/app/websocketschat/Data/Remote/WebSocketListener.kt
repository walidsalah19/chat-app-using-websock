package com.app.websocketschat.Data.Remote

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.nio.charset.Charset

class WebSocketListener(
    private val onMessageReceived:(String)-> Unit,
    private val onOpen: () -> Unit,
    private val onClose: () -> Unit,
    private val onFailure: (Throwable) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        // Connection opened
        onOpen()
        println("WebSocket connection opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        // Text message received
       // val chatMessage = convertJsonToMessage(text)
        onMessageReceived(text)
        println("Received message: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // Binary message received
        val text = bytes.string(Charset.forName("UTF-8"))
        onMessageReceived(text.replace("Received bytes:",""))
        Log.e("bytes","Received bytes: $text")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        // Connection failure
        onFailure(t)
        println("WebSocket connection failure: ${t.message}")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        // Connection closed
        webSocket.close(1000, "close")
        onClose()
        println("WebSocket connection closed: $reason")
    }
}
