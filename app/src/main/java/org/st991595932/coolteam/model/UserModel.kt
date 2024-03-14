package org.st991595932.coolteam.model

import com.google.firebase.Timestamp


class UserModel {
    private var phone: String = ""
    private var userName: String = ""
    private var createTimeStamp: Timestamp? = null
    private var userId:String=""

    constructor() {}

    constructor(phone: String, username: String, createdTimestamp: Timestamp, userId:String) {
        this.phone = phone
        this.userName = username
        this.createTimeStamp = createdTimestamp
        this.userId = userId
    }


    constructor(phone: String?) {
        this.phone = phone!!
    }

    fun getUsername(): String {
        return this.userName
    }

    fun setUsername(username: String) {
        this.userName  = username
    }

    fun getCreatedTimestamp(): Timestamp? {
        return this.createTimeStamp
    }

    fun getPhone(): String {
        return this.phone
    }
    fun setPhone(phone: String) {
        this.phone  = phone
    }
    fun getUserId(): String {
        return this.userName
    }

    fun setUserId(username: String) {
        this.userName  = username
    }


}