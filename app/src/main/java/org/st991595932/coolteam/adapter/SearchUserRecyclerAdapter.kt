package org.st991595932.coolteam.adapter

import org.st991595932.coolteam.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import org.st991595932.coolteam.ChatActivity
import org.st991595932.coolteam.model.ChatMessageModel
import org.st991595932.coolteam.model.UserModel
import org.st991595932.coolteam.utils.AndroidUtil
import org.st991595932.coolteam.utils.FirebaseUtil


class SearchUserRecyclerAdapter(options: FirestoreRecyclerOptions<UserModel>,
                         private val context: Context) : FirestoreRecyclerAdapter<UserModel, SearchUserRecyclerAdapter.UserModelViewHolder>(options) {


    override fun onBindViewHolder(holder: UserModelViewHolder, position: Int, model: UserModel, ) {
        holder.usernameText.text = model.getUsername()
        holder.phoneText.setText(model.getPhone())
        if (model.getUserId().equals(FirebaseUtil.currentUserId())) {
            holder.usernameText.text = model.getUsername() + " (Me)"
        }

        FirebaseUtil.getOtherProfilePicStorageRef(model.getUserId()).getDownloadUrl()
            .addOnCompleteListener { t ->
                if (t.isSuccessful()) {
                    val uri: Uri = t.getResult()
                    AndroidUtil.setProfilePic(context, uri, holder.profilePic)
                }
            }
        holder.itemView.setOnClickListener { v: View? ->
            //navigate to chat activity
            val intent: Intent =
                Intent(context, ChatActivity::class.java)
            AndroidUtil.passUserModelAsIntent(intent, model)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int, ): UserModelViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row, parent, false)
        return UserModelViewHolder(view)
    }

    inner class UserModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var usernameText: TextView
        var phoneText: TextView
        var profilePic: ImageView

        init {
            usernameText = itemView.findViewById(R.id.user_name_text)
            phoneText = itemView.findViewById(R.id.phone_text)
            profilePic = itemView.findViewById(R.id.profile_pic_image_view)
        }
    }
}