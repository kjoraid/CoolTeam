package org.st991595932.coolteam

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import org.st991595932.coolteam.adapter.ChatRecyclerAdapter
import org.st991595932.coolteam.model.ChatMessageModel
import org.st991595932.coolteam.model.ChatroomModel
import org.st991595932.coolteam.model.UserModel
import org.st991595932.coolteam.utils.AndroidUtil
import org.st991595932.coolteam.utils.FirebaseUtil
import java.util.Arrays


class ChatActivity : AppCompatActivity() {

    lateinit var otherUser: UserModel
    lateinit var chatroomId: String
    lateinit var chatroomModel: ChatroomModel
    lateinit var adapter: ChatRecyclerAdapter


    lateinit var messageInput: EditText
    lateinit var sendMessageBtn: ImageButton
    lateinit var backBtn: ImageButton
    lateinit var otherUsername: TextView
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        otherUser = AndroidUtil.getUserModelFromIntent(intent)
        chatroomId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId().toString(),otherUser.getUserId());


        messageInput = findViewById(R.id.chat_message_input);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        backBtn = findViewById(R.id.back_btn);
        otherUsername = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.chat_recycler_view);

        backBtn.setOnClickListener {
            onBackPressed()
        }
        otherUsername.text = otherUser.getUsername()

        sendMessageBtn.setOnClickListener{

            val message = messageInput.getText().toString().trim { it <= ' ' }
            if (message.isEmpty()) return@setOnClickListener
            sendMessageToUser(message)
        }
        getOrCreateChatroomModel()
        setupChatRecyclerView()
    }

    fun setupChatRecyclerView() {
        val query: Query = FirebaseUtil.getChatroomMessageReference(chatroomId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
        //val options = FirestoreRecyclerOptions.Builder<ChatMessageModel>()
        //    .setQuery(query, ChatMessageModel::class.java).build()

        val options: FirestoreRecyclerOptions<ChatMessageModel> = FirestoreRecyclerOptions.Builder<ChatMessageModel>()
            .setQuery(query, ChatMessageModel::class.java)
            .build()

        adapter = ChatRecyclerAdapter(options, applicationContext)

        val manager = LinearLayoutManager(this)
        manager.setReverseLayout(true)

        recyclerView.setLayoutManager(manager)
        recyclerView.setAdapter(adapter)
        adapter.startListening()

        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                recyclerView.smoothScrollToPosition(0)
            }
        })
    }

    fun getOrCreateChatroomModel() {
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                chatroomModel = task.result.toObject(ChatroomModel::class.java)!!
                if (chatroomModel == null) {
                    //first time chat
                    chatroomModel = ChatroomModel(
                        chatroomId,
                        Arrays.asList(FirebaseUtil.currentUserId(), otherUser.getUserId()),
                        Timestamp.now(),
                        "",""
                    )
                    FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel)
                }
            }
        }
    }

    fun sendMessageToUser(message: String?) {
        chatroomModel.setLastMessageTimestamp(Timestamp.now())
        chatroomModel.setLastMessageSenderId(FirebaseUtil.currentUserId().toString())
        chatroomModel.setLastMessage(message!!)
        FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel)
        val chatMessageModel =
            ChatMessageModel(message, FirebaseUtil.currentUserId().toString(), Timestamp.now())
        FirebaseUtil.getChatroomMessageReference(chatroomId).add(chatMessageModel)
            .addOnCompleteListener(OnCompleteListener<DocumentReference?> { task ->
                if (task.isSuccessful) {
                    messageInput.setText("")

                }
            })
    }

}