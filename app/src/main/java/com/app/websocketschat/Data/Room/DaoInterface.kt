package com.app.websocketschat.Data.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DaoInterface {
    @Insert
    fun insertMessage(chatMessage: EntityMessage)

    @Query("SELECT * FROM  messages_table")
    fun getAllMessages(): Flow<List<EntityMessage>>
}