package com.app.websocketschat.Di

import com.app.websocketschat.Data.Remote.WebSocketService
import com.app.websocketschat.Data.Repostories.WebSocketRepositoryImpl
import com.app.websocketschat.Domain.Repostories.WebSocketRepository
import com.app.websocketschat.Domain.UseCases.CloseConnectionUseCase
import com.app.websocketschat.Domain.UseCases.ConnectUseCase
import com.app.websocketschat.Domain.UseCases.SendMessageUseCase
import com.app.websocketschat.Domain.UseCases.WebSocketUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideWebSocketRepository(webSocketService: WebSocketService): WebSocketRepository {
        return WebSocketRepositoryImpl(webSocketService)
    }

    @Provides
    fun provideWebSocketUseCases(repository: WebSocketRepository): WebSocketUseCases {
        return WebSocketUseCases(
            connect = ConnectUseCase(repository),
            sendMessage = SendMessageUseCase(repository),
            closeConnection = CloseConnectionUseCase(repository)
        )
    }
}