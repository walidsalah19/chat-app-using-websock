package com.app.websocketschat.Data.Remote

import com.app.websocketschat.Domain.Modules.Messages
import com.google.gson.Gson
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
        client.dispatcher().executorService().shutdown()
    }

    fun send(message: Messages) {
        val mes=serializeMessage(message =message)
        webSocket?.send(mes)
    }

    fun close() {
        webSocket?.close(1000, "close")
    }
    fun serializeMessage(message: Messages): String {
        return Gson().toJson(message)
    }

}

