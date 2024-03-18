package org.st991595932.coolteam


import android.annotation.SuppressLint
import org.st991595932.coolteam.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import org.st991595932.coolteam.adapter.RecentChatRecyclerAdapter
import org.st991595932.coolteam.model.ChatroomModel
import org.st991595932.coolteam.utils.FirebaseUtil




class ChatFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecentChatRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        setupRecyclerView()
        return view
    }
    fun setupRecyclerView() {
        var query: Query = FirebaseUtil.allChatroomCollectionReference()
            .whereArrayContains("userIds", FirebaseUtil.currentUserId())
            .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING)

        var options: FirestoreRecyclerOptions<ChatroomModel> = FirestoreRecyclerOptions.Builder<ChatroomModel>()
            .setQuery(query, ChatroomModel::class.java).build()

        adapter = RecentChatRecyclerAdapter(options, requireContext())

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        if(adapter!=null)
            adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        if(adapter!=null)
            adapter.stopListening()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if(adapter!=null)
            adapter.notifyDataSetChanged()
    }

}