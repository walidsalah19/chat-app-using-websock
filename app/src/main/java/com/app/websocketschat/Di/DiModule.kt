package com.app.websocketschat.Di

import android.content.Context
import com.app.websocketschat.Data.Remote.WebSocketService
import com.app.websocketschat.Data.Repostories.WebSocketRepositoryImpl
import com.app.websocketschat.Data.Room.DaoInterface
import com.app.websocketschat.Data.Room.RoomData
import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import com.app.websocketschat.Domain.UseCases.CloseConnectionUseCase
import com.app.websocketschat.Domain.UseCases.ConnectUseCase
import com.app.websocketschat.Domain.UseCases.SendMessageUseCase
import com.app.websocketschat.Domain.UseCases.WebSocketUseCases
import com.app.websocketschat.Domain.UseCases.GetAllMessagesUseCase
import com.app.websocketschat.Domain.UseCases.ReceivedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Provides
    fun provideWebSocketService(): WebSocketService {
        return WebSocketService("ws://192.168.56.1:3000")
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RoomData {
        return RoomData.getInstance(appContext)
    }

    @Provides
    fun provideMessageDao(db: RoomData): DaoInterface {
        return db.dao()
    }

    @Provides
    fun provideWebSocketRepository(webSocketService: WebSocketService,dao:DaoInterface): WebSocketRepository {
        return WebSocketRepositoryImpl(webSocketService,dao)
    }

    @Provides
    fun provideWebSocketUseCases(repository: WebSocketRepository): WebSocketUseCases {
        return WebSocketUseCases(
            connect = ConnectUseCase(repository),
            sendMessage = SendMessageUseCase(repository),
            closeConnection = CloseConnectionUseCase(repository),
            received = ReceivedUseCase(repository),
            getAllMessagesUseCase = GetAllMessagesUseCase(repository)
        )
    }
}