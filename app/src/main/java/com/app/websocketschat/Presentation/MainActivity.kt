package com.app.websocketschat.Presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.websocketschat.Domain.Models.Messages
import com.app.websocketschat.Presentation.Adapter.AdapterClass
import com.app.websocketschat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WebSocketViewModel by viewModels()
    private var listMessages = ArrayList<Messages>()

    private val adapter: AdapterClass by lazy {
        AdapterClass(listMessages)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getAllMessages()
        getReceivedMessage()
        sendMessage()

    }

    fun initRecyclerView() {
        binding.recyclerviewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewChat.adapter = adapter
        adapter.senderIdAdd(getMacAddress())
    }

    fun getAllMessages() {
        lifecycleScope.launchWhenStarted {
            viewModel.messages.collect {
                listMessages.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun getReceivedMessage() {
        viewModel.receivedData().observe(this, Observer { received ->
            Log.e("rData", received.message)
            listMessages.add(received)
            adapter.notifyDataSetChanged()
        })
    }

    fun sendMessage() {
        binding.sendMessage.setOnClickListener {

            val m =
                Messages(binding.sendMessageText.text.toString(), getMacAddress(), "", getTime())
            viewModel.connect()
            viewModel.sendMessage(m)
            viewModel.closeConnection()

        }
    }

    fun getTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)

        return formattedDate
    }

    fun getMacAddress(): String {
        try {
            val all = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue

                val macBytes = nif.hardwareAddress ?: return "02:00:00:00:00:00"
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }

                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
            // Handle exception
        }
        return "02:00:00:00:00:00"
    }

    override fun onDestroy() {
        super.onDestroy()
        // Send a message
        viewModel.closeConnection()
    }
}