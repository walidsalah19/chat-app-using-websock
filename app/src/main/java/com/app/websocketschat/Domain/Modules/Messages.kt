package com.app.websocketschat.Domain.Modules

import java.io.Serializable

data class Messages( val message: String,
                      val senderId: String,
                      val receiverId: String,
                      val timestamp: String): Serializable