
package org.st991595932.coolteam.adapter

import org.st991595932.coolteam.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import org.st991595932.coolteam.ChatActivity
import org.st991595932.coolteam.model.ChatroomModel
import org.st991595932.coolteam.model.UserModel
import org.st991595932.coolteam.utils.AndroidUtil
import org.st991595932.coolteam.utils.FirebaseUtil


class RecentChatRecyclerAdapter(
    options: FirestoreRecyclerOptions<ChatroomModel>,
    private val context: Context,
) : FirestoreRecyclerAdapter<ChatroomModel, RecentChatRecyclerAdapter.ChatroomModelViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatroomModelViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row, parent, false)
        return ChatroomModelViewHolder(view)
    }
    override fun onBindViewHolder(
         holder: ChatroomModelViewHolder,
        position: Int,
        model: ChatroomModel,
    ) {
        FirebaseUtil.getOtherUserFromChatroom(model.getUserIds())
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val lastMessageSentByMe =
                        model.getLastMessageSenderId() == FirebaseUtil.currentUserId()
                    val otherUserModel = task.result.toObject(UserModel::class.java)
                    FirebaseUtil.getOtherProfilePicStorageRef(otherUserModel!!.getUserId())
                        .getDownloadUrl()
                        .addOnCompleteListener { t ->
                            if (t.isSuccessful) {
                                val uri = t.result
                                AndroidUtil.setProfilePic(context, uri, holder.profilePic)
                            }
                        }
                    FirebaseUtil
                    holder.usernameText.text = otherUserModel.getUsername()
                    if (lastMessageSentByMe) holder.lastMessageText.text =
                        "You : " + model.getLastMessage() else holder.lastMessageText.text =
                        model.getLastMessage()

                    holder.lastMessageTimeText.setText(FirebaseUtil.timestampToString(model.getLastMessageTimestamp()!!))
                    holder.itemView.setOnClickListener { v: View? ->
                        //navigate to chat activity
                        val intent =
                            Intent(context, ChatActivity::class.java)
                        AndroidUtil.passUserModelAsIntent(intent, otherUserModel)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                }
            }
    }

    inner class ChatroomModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var usernameText: TextView
        var lastMessageText: TextView
        var lastMessageTimeText: TextView
        var profilePic: ImageView

        init {
            usernameText = itemView.findViewById(R.id.user_name_text)
            lastMessageText = itemView.findViewById(R.id.last_message_text)
            lastMessageTimeText = itemView.findViewById(R.id.last_message_time_text)
            profilePic = itemView.findViewById(R.id.profile_pic_image_view)
        }
    }


}