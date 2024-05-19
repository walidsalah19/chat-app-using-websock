package com.app.websocketschat.Presentation

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.websocketschat.Domain.Modules.Messages
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
        viewModel.connect()
        initRecyclerView()
        getAllMessages()
        sendMessage()

    }

    fun initRecyclerView() {
        binding.recyclerviewChat.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewChat.adapter = adapter
        adapter.senderIdAdd(getAndroidId(this@MainActivity))
    }

    fun getAllMessages() {
        lifecycleScope.launchWhenStarted {
            viewModel.messages.collect {
                listMessages.clear()
                listMessages.addAll(it)
                adapter.notifyDataSetChanged()
                binding.recyclerviewChat.scrollToPosition(listMessages.size - 1)

            }
        }
    }

    fun sendMessage() {
        binding.sendMessage.setOnClickListener {

            val m =
                Messages(binding.sendMessageText.text.toString(), getAndroidId(this@MainActivity), "", getTime())
            viewModel.sendMessage(m)
            binding.sendMessageText.setText("")

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

                val macBytes = nif.hardwareAddress ?: return "02:00:00:00:00"
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
        return "02:00:00:00:00"
    }
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Send a message
        viewModel.closeConnection()
    }
}