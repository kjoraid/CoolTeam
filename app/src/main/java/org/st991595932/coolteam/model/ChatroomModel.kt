package org.st991595932.coolteam.model

import com.google.firebase.Timestamp

class ChatroomModel {
    private var chatroomId: String = ""
    private var userIds: List<String> = listOf()
    private var lastMessageTimestamp: Timestamp? = null
    private var lastMessageSenderId: String = ""
    private var lastMessage: String = ""

    constructor(){}

    constructor(chatroomId: String, userIds: List<String>, lastMessageTimestamp:Timestamp,
                lastMessageSenderId:String, lastMessage:String  ) {
        this.chatroomId = chatroomId
        this.userIds = userIds
        this.lastMessageTimestamp = lastMessageTimestamp
        this.lastMessageSenderId = lastMessageSenderId
        this.lastMessage = lastMessage
    }

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

    fun getLastMessageTimestamp(): Timestamp? {
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