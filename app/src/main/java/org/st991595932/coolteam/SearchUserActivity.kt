package org.st991595932.coolteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import org.st991595932.coolteam.adapter.SearchUserRecyclerAdapter
import org.st991595932.coolteam.databinding.ActivitySearchUserBinding
import org.st991595932.coolteam.model.UserModel
import org.st991595932.coolteam.utils.FirebaseUtil

class SearchUserActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var recyclerView: RecyclerView

    private var adapter: SearchUserRecyclerAdapter? = null

    private lateinit var binding: ActivitySearchUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        searchInput = binding.searchUsernameInput
        searchButton = binding.searchUserBtn
        backButton = binding.backBtn
        recyclerView = binding.searchUserRecyclerView

        searchInput.requestFocus()

        backButton.setOnClickListener{
            onBackPressed()
        }

        searchButton.setOnClickListener{
            var searchName = searchInput.text.toString()
            if (searchName.isEmpty() || searchName.length<3) {
                searchInput.setError("Invalid username")
                return@setOnClickListener
            }

            setupSearchRecyclerView(searchName)
        }
    }

    fun setupSearchRecyclerView(searchname:String) {
        var query: Query = FirebaseUtil.allUserCollectionReference()
            .whereGreaterThanOrEqualTo("username", searchname)
            .whereLessThan("username", searchname + "\ufffd")

            //.whereLessThan("username", searchname + "\uf8ff")

            //.whereGreaterThanOrEqualTo("username", searchInput.text.toString())

        var options: FirestoreRecyclerOptions<UserModel> = FirestoreRecyclerOptions.Builder<UserModel>()
            .setQuery(query, UserModel::class.java).build()

        adapter = SearchUserRecyclerAdapter(options, applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter!!.startListening()
    }

    override fun onStart() {
        super.onStart()
        if(adapter!=null)
            adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        if(adapter!=null)
            adapter?.stopListening()
    }

    override fun onResume() {
        super.onResume()
        if(adapter!=null)
            adapter?.startListening()
    }


}