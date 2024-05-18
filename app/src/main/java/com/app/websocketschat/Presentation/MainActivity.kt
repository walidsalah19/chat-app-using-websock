package com.app.websocketschat.Presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.app.websocketschat.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.lifecycle.Observer


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.connect()

       /* viewModel.messages.observe(this, Observer { messages ->
            Log.e("message",messages.get(messages.size-1).message)
        })

        findViewById<Button>(R.id.send_message).setOnClickListener {
            val message = findViewById<EditText>(R.id.send_message_text).text.toString()
            viewModel.sendMessage("User", message)
        }*/
    }
}