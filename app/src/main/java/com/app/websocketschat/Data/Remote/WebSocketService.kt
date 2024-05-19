package com.app.websocketschat.Data.Remote

import com.app.websocketschat.Domain.Models.Messages
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class WebSocketService(private val url: String) {
    private var webSocket: WebSocket? = null
    private var listener: WebSocketListener? = null

    fun start(listener: WebSocketListener) {
        this.listener = listener
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = client.newWebSocket(request, listener)
    }

    fun send(message: Messages) {
        val message=convertMessage(message =message)
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "close")
    }
    fun convertMessage(message: Messages):String{
        return message.message+","+message.senderId+","+message.receiverId+","+message.timestamp
    }
}
