package com.app.websocketschat.Domain.Mapper

import com.app.websocketschat.Data.Room.EntityMessage
import com.app.websocketschat.Domain.Modules.Messages

object Mapper {
    fun convertToMessages(entiry:List<EntityMessage>):List<Messages>{
        return entiry.map { Messages(it.message,it.senderId,it.receiverId,it.timestamp) }
    }
}