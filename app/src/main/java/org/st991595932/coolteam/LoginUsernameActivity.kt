package org.st991595932.coolteam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import org.st991595932.coolteam.databinding.ActivityLoginUsernameBinding
import org.st991595932.coolteam.model.UserModel
import org.st991595932.coolteam.utils.AndroidUtil
import org.st991595932.coolteam.utils.FirebaseUtil


class LoginUsernameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUsernameBinding

    lateinit var userNameInput : EditText
    lateinit var letMeInBtn: Button
    lateinit var progressBar: ProgressBar
    var phoneNumber:String = ""
    var userModel : UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding the xml file
        binding = ActivityLoginUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userNameInput = binding.loginUserName
        letMeInBtn = binding.loginLoginButton
        progressBar = binding.loginProgressBar

        phoneNumber = intent.getStringExtra("Phone").toString()
        getUsername()

        letMeInBtn.setOnClickListener{
            setUserName()
        }

    }

    fun setUserName(){
        val userName:String = userNameInput.text.toString()
        if(userName.isEmpty() || userName.length <3) {
            userNameInput.setError("Username length should be at least 3 chars")
            return
        }
        setInProgress(true)

        if(userModel!=null){
            userModel?.setUsername(userName)
        }else {
            userModel = UserModel(phoneNumber, userName, Timestamp.now(),
                FirebaseUtil.currentUserId().toString()
            )
        }

        FirebaseUtil.currentUserDetails().set(userModel!!).addOnCompleteListener { task ->
            setInProgress(false)
            if (task.isSuccessful) {
                val intent = Intent(
                    this@LoginUsernameActivity,
                    MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }

    }

    private fun getUsername() {
        setInProgress(true)
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener{ task ->
            setInProgress(false)
            if (task.isSuccessful && task.result.toObject(UserModel::class.java) != null) {
                userModel = task.result.toObject(UserModel::class.java)!!
                if (userModel!=null)
                    userNameInput.setText(userModel!!.getUsername())
            }
        }
    }

fun setInProgress(inProgress: Boolean) {
        if (inProgress){
            progressBar.visibility = View.VISIBLE
            letMeInBtn.visibility = View.INVISIBLE
        }else {
            progressBar.visibility = View.INVISIBLE
            letMeInBtn.visibility = View.VISIBLE
        }
    }

    fun createNewUser(userName: String) {
        if (userModel != null){
            userModel!!.setUsername(userName)
        }else {
            userModel = UserModel(phoneNumber, userName, Timestamp.now(),
                FirebaseUtil.currentUserId().toString()
            )
        }
    }
}



