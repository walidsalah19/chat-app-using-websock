package com.app.websocketschat.Presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.app.websocketschat.Data.Remote.MyWebSocketListener
import com.app.websocketschat.Data.Remote.WebSocketManager
import com.app.websocketschat.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WebSocketViewModel by viewModels()
    //private lateinit var webSocketManager: WebSocketManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.connect()

        viewModel.messages.observe(this, Observer { message ->
            // Update UI with the received message
        })

        // Send a message
        viewModel.sendMessage("Hello WebSocket")

        // Close the connection
        viewModel.closeConnection()




      /*  webSocketManager = WebSocketManager()

        val webSocketUrl = "ws://192.168.56.1:3000"
        val webSocketListener = MyWebSocketListener()

        // Connect to the WebSocket server
        webSocketManager.connect(webSocketUrl, webSocketListener)

        // Send a message
        val message = "Walid Salah"
        webSocketManager.sendMessage(message)
        // viewModel.connect()
*//*
        viewModel.messages.observe(this, Observer { messages ->
            Log.e("message",messages.get(messages.size-1).message)
        })*//*

       *//* findViewById<Button>(R.id.send_message).setOnClickListener {
            val message = findViewById<EditText>(R.id.send_message_text).text.toString()
            viewModel.sendMessage("User", message)
        }*/
    }
}