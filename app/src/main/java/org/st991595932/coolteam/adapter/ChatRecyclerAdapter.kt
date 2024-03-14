package org.st991595932.coolteam.adapter

//noinspection SuspiciousImport
//import android.R

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import org.st991595932.coolteam.R
import org.st991595932.coolteam.model.ChatMessageModel
import org.st991595932.coolteam.utils.FirebaseUtil


class ChatRecyclerAdapter(options: FirestoreRecyclerOptions<ChatMessageModel>,
                          private val context: Context) : FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatModelViewHolder>(options) {

    // Other methods and properties of the adapter
    //lateinit var context: Context


     override fun onBindViewHolder(holder: ChatModelViewHolder, position: Int,
         model: ChatMessageModel,
     ) {

        if (model.getSenderId() == FirebaseUtil.currentUserId()) {
            holder.leftChatLayout.visibility = View.GONE
            holder.rightChatLayout.visibility = View.VISIBLE
            holder.rightChatTextview.text = model.getMessage()
        } else {
            holder.rightChatLayout.visibility = View.GONE
            holder.leftChatLayout.visibility = View.VISIBLE
            holder.leftChatTextview.text = model.getMessage()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatModelViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(
                R.layout.chat_message_recycler_row,
                parent,
                false
            )
        return ChatModelViewHolder(view)
    }

    class ChatModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var leftChatLayout: LinearLayout
        var rightChatLayout: LinearLayout
        var leftChatTextview: TextView
        var rightChatTextview: TextView

        init {
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout)
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout)
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview)
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview)
        }
    }


}