package org.st991595932.coolteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.hbb20.CountryCodePicker
import org.st991595932.coolteam.databinding.ActivityLoginPhoneNumberBinding


class LoginPhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPhoneNumberBinding

    private lateinit var countryCodePicker: CountryCodePicker
    private lateinit var phoneInput: EditText
    private lateinit var sendOTPBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding the xml file
        binding = ActivityLoginPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countryCodePicker = binding.loginCountryCode
        phoneInput = binding.loginMobileNumber
        sendOTPBtn = binding.sendOtpButton
        progressBar = binding.loginProgressBar

        // invisible the progress bar
        progressBar.visibility = View.INVISIBLE

        countryCodePicker.registerCarrierNumberEditText(phoneInput)
        sendOTPBtn.setOnClickListener {v: View ->
            if(!countryCodePicker.isValidFullNumber()) {
                phoneInput.setError("Phone number is not valid")
                return@setOnClickListener
            }
            val intent = Intent(this@LoginPhoneNumberActivity, LoginOtpActivity::class.java)
            intent.putExtra("Phone", countryCodePicker.fullNumberWithPlus)
            startActivity(intent)
        }

    }
}