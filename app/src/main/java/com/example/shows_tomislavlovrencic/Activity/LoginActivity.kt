package com.example.shows_tomislavlovrencic.Activity

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shows_tomislavlovrencic.Models.UserTokenViewModel
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.classes.User
import kotlinx.android.synthetic.main.activity_login.*

var WENT_WRONG = "Something went wrong,please try again"
var INTERNET_CONNECTION = "Please check your internet connection"

const val UP_LIMIT = 8

lateinit var sharedPrefs: SharedPreferences

lateinit var sharedPrefsEditor: SharedPreferences.Editor

var rememberMe = false

var userActive: User? = null

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserTokenViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mProgressDialog = ProgressDialog(this)


        buttonLogin.setOnClickListener {
            rememberMe = checkBoxLogin.isChecked
            checkIfAccountExists()
        }

        textViewCreateAnAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun isValidEmail(string: String): Boolean {
            return string.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(string).matches()
        }

        editTextUsernameLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editTextUsernameLogin.text.toString().isNotEmpty() && editTextPasswordLogin.text.toString().length >= UP_LIMIT
                    && isValidEmail(editTextUsernameLogin.text.toString())
                ) {
                    buttonLogin.isEnabled = true
                    buttonLogin.setBackgroundColor(resources.getColor(R.color.Before))
                } else {
                    buttonLogin.isEnabled = false
                    buttonLogin.setBackgroundColor(resources.getColor(R.color.After))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        editTextPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editTextUsernameLogin.text.toString().isNotEmpty() && editTextPasswordLogin.text.toString().length >= UP_LIMIT
                    && isValidEmail(editTextUsernameLogin.text.toString())
                ) {
                    buttonLogin.isEnabled = true
                    buttonLogin.setBackgroundColor(resources.getColor(R.color.Before))
                } else {
                    buttonLogin.isEnabled = false
                    buttonLogin.setBackgroundColor(resources.getColor(R.color.After))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun checkIfAccountExists() {

        viewModel = ViewModelProviders.of(this).get(UserTokenViewModel::class.java)

        var user = User(
            email = editTextUsernameLogin.text.toString(),
            password = editTextPasswordLogin.text.toString(),
            showId = ""
        )
        viewModel.resetLiveData()
        viewModel.getData(user, rememberMe)

        viewModel.liveTokenData.observe(this, Observer { response ->
            if (response != null) {
                if (response.isSuccessful) {
                    val intent = Intent(this@LoginActivity, FragmentContainerActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    viewModel.resetLiveData()
                    val dialog = AlertDialog.Builder(this@LoginActivity)
                    dialog.setTitle(response.message)
                    dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }

        })
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}

