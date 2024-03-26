package org.st991595932.coolteam

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import org.st991595932.coolteam.model.UserModel
import org.st991595932.coolteam.utils.AndroidUtil
import org.st991595932.coolteam.utils.FirebaseUtil


class ProfileFragment : Fragment() {
    private lateinit var profilePic: ImageView
    private lateinit var usernameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var updateProfileBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var logoutBtn: TextView
    private lateinit var currentUserModel: UserModel

    lateinit var imagePickLauncher: ActivityResultLauncher<Intent>
    lateinit var selectedImageUri: Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imagePickLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null && data.data != null) {
                    selectedImageUri = data.data!!
                    AndroidUtil.setProfilePic(context, selectedImageUri, profilePic)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        var view = inflater.inflate(R.layout.fragment_profile, container, false)

        profilePic = view.findViewById(R.id.profile_image_view)
        usernameInput = view.findViewById(R.id.profile_username)
        phoneInput = view.findViewById(R.id.profile_phone)
        progressBar = view.findViewById(R.id.profile_progress_bar)
        updateProfileBtn = view.findViewById(R.id.profle_update_btn)
        logoutBtn = view.findViewById(R.id.logout_btn)

        getUserData()

        updateProfileBtn.setOnClickListener() {

            updateBtnClick()
        }

        logoutBtn.setOnClickListener() {
            FirebaseUtil.logout()
            val intent = Intent(context, SplashActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        profilePic.setOnClickListener(){
           ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                .createIntent {
                    object : Function1<Intent, Unit> {
                        override fun invoke(p1: Intent): Unit {
                            imagePickLauncher.launch(p1)
                            return Unit

                        }
                    }

                }
        }

        // Inflate the layout for this fragment
        return view
    }

    fun updateBtnClick(){
        val newUserName:String = usernameInput.text.toString()
        if(newUserName.isEmpty() || newUserName.length <3) {
            usernameInput.setError("Username length should be at least 3 chars")
            return
        }
        currentUserModel.setUsername(newUserName)
        setInProgress(true)
        updateToFirestore()


    }

    private fun updateToFirestore() {
        FirebaseUtil.currentUserDetails().set(currentUserModel)
            .addOnCompleteListener() {
                setInProgress(false)

                if(it.isSuccessful){
                    AndroidUtil.showToast(context, "Update Successfully")
                }else {
                    AndroidUtil.showToast(context, "Update failed")
                }
            }

    }
    private fun getUserData() {
        setInProgress(true)
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener() {
            setInProgress(false)
            currentUserModel = it.getResult().toObject(UserModel::class.java)!!
            usernameInput.setText(currentUserModel.getUsername())
            phoneInput.setText(currentUserModel.getPhone())
        }
    }
    private fun setInProgress(inProgress: Boolean) {
        if (inProgress){
            progressBar.visibility = View.VISIBLE
            updateProfileBtn.visibility = View.INVISIBLE
        }else {
            progressBar.visibility = View.INVISIBLE
            updateProfileBtn.visibility = View.VISIBLE
        }
    }

}