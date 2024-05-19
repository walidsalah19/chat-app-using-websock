package com.app.websocketschat.Data.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [EntityMessage::class], version = 1)
abstract class RoomData : RoomDatabase() {

    abstract fun dao(): DaoInterface

    companion object {
        private var instance: RoomData? = null

        @Synchronized
        fun getInstance(ctx: Context): RoomData {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, RoomData::class.java,
                    "messages_table"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}