package com.app.websocketschat.Domain.Models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "messages_table")
data class MessageDataClass(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String,
    val senderId: String,
    val receiverId: String,
    val timestamp: Long
)