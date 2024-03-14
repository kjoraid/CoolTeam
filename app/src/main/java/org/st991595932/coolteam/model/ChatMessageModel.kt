package org.st991595932.coolteam.model

import com.google.firebase.Timestamp

class ChatMessageModel(message: String, currentUserId: String, now: Timestamp) {

    private lateinit var message: String
    private lateinit var senderId: String
    private lateinit var timestamp: Timestamp

    fun ChatMessageModel() {}


    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getSenderId(): String {
        return senderId
    }

    fun setSenderId(senderId: String) {
        this.senderId = senderId
    }

    fun getTimestamp(): Timestamp {
        return timestamp
    }

    fun setTimestamp(timestamp: Timestamp) {
        this.timestamp = timestamp
    }
}