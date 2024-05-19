package com.app.websocketschat.Domain.Models

data class Messages( val message: String,
                      val senderId: String,
                      val receiverId: String,
                      val timestamp: String)