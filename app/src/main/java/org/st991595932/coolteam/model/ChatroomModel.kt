package org.st991595932.coolteam.model

import com.google.firebase.Timestamp

class ChatroomModel(
    chatroomId: String,
    asList: MutableList<String>,
    now: Timestamp,
    s: String,
    s1: String
) {
    private lateinit var chatroomId: String
    private lateinit var userIds: List<String>
    private lateinit var lastMessageTimestamp: Timestamp
    private lateinit var lastMessageSenderId: String
    private lateinit var lastMessage: String

    fun getChatroomId(): String {
        return chatroomId
    }

    fun setChatroomId(chatroomId: String) {
        this.chatroomId = chatroomId
    }

    fun getUserIds(): List<String> {
        return userIds
    }

    fun setUserIds(userIds: List<String>) {
        this.userIds = userIds
    }

    fun getLastMessageTimestamp(): Timestamp {
        return lastMessageTimestamp
    }

    fun setLastMessageTimestamp(lastMessageTimestamp: Timestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp
    }

    fun getLastMessageSenderId(): String {
        return lastMessageSenderId
    }

    fun setLastMessageSenderId(lastMessageSenderId: String) {
        this.lastMessageSenderId = lastMessageSenderId
    }

    fun getLastMessage(): String {
        return lastMessage
    }

    fun setLastMessage(lastMessage: String) {
        this.lastMessage = lastMessage
    }

}