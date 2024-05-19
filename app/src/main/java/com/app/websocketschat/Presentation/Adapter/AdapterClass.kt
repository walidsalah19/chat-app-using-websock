package com.app.websocketschat.Presentation.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.websocketschat.Domain.Models.Messages
import com.app.websocketschat.R

class AdapterClass(private var messagesArrayList: ArrayList<Messages>?) : RecyclerView.Adapter<AdapterClass.Help>() {
    private lateinit var senderId: String
    fun senderIdAdd(senderId: String)
    {
        this.senderId=senderId
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Help {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_adapter_layout, parent, false)
        return Help(view)
    }

    override fun getItemCount(): Int {
        return messagesArrayList!!.size
    }

    override fun onBindViewHolder(holder: Help, position: Int) {
        if (messagesArrayList?.get(position)?.senderId.equals(senderId)) {
            holder.senderLayout.visibility = View.VISIBLE
            holder.receiverLayout.visibility = View.GONE
            holder.senderMessage.setText(messagesArrayList?.get(position)?.message)
            holder.senderTime.setText(messagesArrayList?.get(position)?.timestamp.toString())
        } else {
            holder.senderLayout.visibility = View.GONE
            holder.receiverLayout.visibility = View.VISIBLE
            holder.receiverMessage.setText(messagesArrayList?.get(position)?.message)
            holder.receiverTime.setText(messagesArrayList?.get(position)?.timestamp.toString())
        }
    }




    class Help(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderMessage = itemView.findViewById<TextView>(R.id.sender_message)
        val senderTime = itemView.findViewById<TextView>(R.id.sender_time)
        val senderLayout = itemView.findViewById<RelativeLayout>(R.id.sender_layout)

        val receiverMessage = itemView.findViewById<TextView>(R.id.receiver_message)
        val receiverTime = itemView.findViewById<TextView>(R.id.receiver_time)
        val receiverLayout = itemView.findViewById<RelativeLayout>(R.id.receiver_layout)


    }
}