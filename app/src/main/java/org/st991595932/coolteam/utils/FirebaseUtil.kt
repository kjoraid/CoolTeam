package org.st991595932.coolteam.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseUtil {
    companion object {

        fun currentUserId(): String? {
            return FirebaseAuth.getInstance().uid
        }
        fun currentUserDetails(): DocumentReference {
            return FirebaseFirestore.getInstance().collection("users")
                .document(currentUserId().toString())
        }

        fun isLoggedIn(): Boolean {
            if(currentUserId() != null) {
                return true
            }
            return false
        }

        fun allUserCollectionReference() : CollectionReference {
            return FirebaseFirestore.getInstance().collection("users")
        }

        fun getChatroomReference(chatroomId: String?): DocumentReference {
            return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId!!)
        }
        fun getChatroomId(userId1: String, userId2: String): String {
            return if (userId1.hashCode() < userId2.hashCode()) {
                userId1 + "_" + userId2
            } else {
                userId2 + "_" + userId1
            }
        }


        fun getChatroomMessageReference(chatroomId: String?): CollectionReference {
            return getChatroomReference(chatroomId).collection("chats")
        }

        fun getOtherProfilePicStorageRef(otherUserId: String): StorageReference {
            return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId)
        }
    }
}