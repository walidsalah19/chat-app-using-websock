package com.app.websocketschat.Data.Room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "messages_table")
data class EntityMessage(
    val message: String,
    val senderId: String,
    val receiverId: String,
    val timestamp: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
