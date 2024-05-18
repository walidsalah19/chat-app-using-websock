package com.app.websocketschat.Di

import android.content.Context
import androidx.room.Room
import com.app.websocketschat.Data.Remote.WebSocketService
import com.app.websocketschat.Data.Repostories.ChatRepositoryImpl
import com.app.websocketschat.Data.Room.DaoInterface
import com.app.websocketschat.Data.Room.RoomData
import com.app.websocketschat.Domain.ChatUseCases
import com.app.websocketschat.Domain.CloseChatConnectionUseCase
import com.app.websocketschat.Domain.ConnectChatUseCase
import com.app.websocketschat.Domain.GetAllMessagesUseCase
import com.app.websocketschat.Domain.Repostories.ChatRepository
import com.app.websocketschat.Domain.SendChatMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideWebSocketService(): WebSocketService {
        return WebSocketService("wss://your-websocket-url")
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RoomData {
        return RoomData.getInstance(appContext)
    }

    @Provides
    fun provideChatMessageDao(db: RoomData): DaoInterface {
        return db.dao()
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        webSocketService: WebSocketService,
        chatMessageDao: DaoInterface
    ): ChatRepository {
        return ChatRepositoryImpl(webSocketService, chatMessageDao)
    }

    @Provides
    @Singleton
    fun provideChatUseCases(repository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            connect = ConnectChatUseCase(repository),
            sendMessage = SendChatMessageUseCase(repository),
            closeConnection = CloseChatConnectionUseCase(repository),
            getAllMessages = GetAllMessagesUseCase(repository)
        )
    }

}