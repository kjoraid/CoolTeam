package org.st991595932.coolteam

import android.content.Intent
import android.credentials.Credential
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import org.st991595932.coolteam.databinding.ActivityLoginOtpBinding
import org.st991595932.coolteam.utils.AndroidUtil
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask

class LoginOtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOtpBinding
    var phoneNumber:String = ""
    var timeoutSeconds: Long = 60L;
    var verificationCode: String = ""
    lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken


    lateinit var otpInput: EditText
    lateinit var nextBtn: Button
    lateinit var progressBar: ProgressBar
    lateinit var resendOtpTextView: TextView
    var mAuth:FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        otpInput = binding.loginOtp
        nextBtn = binding.loginNextButton
        progressBar = binding.loginProgressBar
        resendOtpTextView = binding.resendOtpTextView

        phoneNumber = intent.getStringExtra("Phone").toString()
        //Toast.makeText(applicationContext, phoneNumber, Toast.LENGTH_SHORT).show()
        sendOtp(phoneNumber, false)

        nextBtn.setOnClickListener{view:View ->
            run {
                val enteredOtp: String = otpInput.text.toString()
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    verificationCode,
                    enteredOtp
                )
                signIn(credential)
                setInProgress(true)
            }
        }
        resendOtpTextView.setOnClickListener{ v ->
            sendOtp(phoneNumber, true)
        }
    }

    fun sendOtp(phoneNumber: String, isResend: Boolean) {
        startResentTimer()
        setInProgress(true)

        val builder: PhoneAuthOptions.Builder =
            PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object:PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        signIn(p0)
                        setInProgress(false)
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        AndroidUtil.showToast(applicationContext, "OTP verification failed")
                        setInProgress(false)
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(p0, p1)
                        verificationCode = p0
                        forceResendingToken = p1
                        AndroidUtil.showToast(applicationContext, "OTP sent successfully")
                        setInProgress(false)
                    }

                })
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(forceResendingToken).build())
        }else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build())
        }

    }

    fun setInProgress(inProgress: Boolean) {
        if (inProgress){
            progressBar.visibility = View.VISIBLE
            nextBtn.visibility = View.INVISIBLE
        }else {
            progressBar.visibility = View.INVISIBLE
            nextBtn.visibility = View.VISIBLE
        }
    }

    fun signIn(phoneAuthCredential: PhoneAuthCredential) {
        // Log in and move to the next Activity
        setInProgress(true)
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener { task ->
            setInProgress(false)
            if (task.isSuccessful) {
                // Handle sign-in success
                intent = Intent(this@LoginOtpActivity, LoginUsernameActivity::class.java)
                intent.putExtra("Phone", phoneNumber)
                startActivity(intent)
            } else {
                // Handle sign-in failure
                AndroidUtil.showToast(applicationContext, "OTP verification failed")
            }
        }
    }

    private fun startResentTimer() {
        resendOtpTextView.isSaveEnabled = false
        var timer = Timer()
        timer.scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                timeoutSeconds --
                resendOtpTextView.setText("Resent OTP in $timeoutSeconds seconds")
                if (timeoutSeconds<=0) {
                    timeoutSeconds = 60L
                    timer.cancel()
                    runOnUiThread {  ->
                        resendOtpTextView.isEnabled = true
                    }
                }
            }

        }, 0, 1000)

    }


}



