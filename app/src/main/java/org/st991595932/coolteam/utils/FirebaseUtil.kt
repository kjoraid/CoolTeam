package org.st991595932.coolteam.utils

import android.content.Context
import android.util.Log
import com.google.ai.client.generativeai.type.content
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseUtil {
    companion object {

        fun currentUserId(): String {
            return FirebaseAuth.getInstance().uid.toString()
        }
        fun currentUserDetails(): DocumentReference {
            return FirebaseFirestore.getInstance().collection("users")
                .document(currentUserId())
        }

        fun isLoggedIn(): Boolean {
            Log.i("Khalid", currentUserId())
            if (currentUserId().equals("null")) {
                return false
            } else {
                return true
            }
        }

        fun allUserCollectionReference() : CollectionReference {
            return FirebaseFirestore.getInstance().collection("users")
        }

        fun getChatroomReference(chatroomId: String): DocumentReference {
           // val safeChatroomId = chatroomId ?: ""
            Log.i("FirebaseUtil", chatroomId)
            // Khalid Joraid to continue Here here is the error
            //var chatRoomRef = FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId)

            return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId)
            //return chatRoomRef
            //return FirebaseFirestore.getInstance().collection("chatrooms").document("4ayjqvq8I7gIsfVLXMNjw5vtt1F3_sEkF57C0ILOOx9Rl7WEB3g5YV342")

        }

        fun getChatroomMessageReference(chatroomId: String): CollectionReference {
            //return getChatroomReference(chatroomId).collection("chats")
            return getChatroomReference(chatroomId).collection("chats")
        }

        fun getChatroomId(userId1: String, userId2: String): String {
            return if (userId1.hashCode() < userId2.hashCode()) {
                userId1 + "_" + userId2
            } else {
                userId2 + "_" + userId1
            }
        }

        fun getOtherProfilePicStorageRef(otherUserId: String): StorageReference {
            return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId)
        }
    }
}