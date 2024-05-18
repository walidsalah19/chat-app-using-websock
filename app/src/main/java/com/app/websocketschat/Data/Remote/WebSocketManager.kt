package com.app.websocketschat.Data.Remote

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class WebSocketManager {

    private var webSocket: WebSocket? = null

    fun connect(url: String, listener: WebSocketListener) {
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

    fun disconnect() {
        webSocket?.cancel()
        webSocket = null
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }
}
