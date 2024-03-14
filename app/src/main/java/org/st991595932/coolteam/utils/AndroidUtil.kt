package org.st991595932.coolteam.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast


import org.st991595932.coolteam.model.UserModel


class AndroidUtil {
    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        }

        fun passUserModelAsIntent(intent: Intent, model: UserModel) {
            intent.putExtra("username", model.getUsername())
            intent.putExtra("phone", model.getPhone())
            intent.putExtra("userId", model.getUserId())
            }

        fun getUserModelFromIntent(intent: Intent): UserModel {
            val userModel = UserModel()
            userModel.setUsername(intent.getStringExtra("username")!!)
            userModel.setPhone(intent.getStringExtra("phone")!!)
            userModel.setUserId(intent.getStringExtra("userId")!!)
            return userModel
        }

        fun setProfilePic(context: Context?, imageUri: Uri?, imageView: ImageView?) {
            //Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform())
             //   .into(imageView)
        }

    }
}