package com.example.shows_tomislavlovrencic.Activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shows_tomislavlovrencic.Models.UserViewModel
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.classes.User
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        buttonRegister.setOnClickListener {
            registerUser()
        }

        btnBackRegister.setOnClickListener {
            var intent = Intent(RegisterActivity@ this, LoginActivity::class.java)
            startActivity(intent)
        }


        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editTextEmail.text.toString().isNotEmpty() && editTextPasswordRegister.text.toString().length >= UP_LIMIT
                    && isValidEmail(editTextEmail.text.toString()) && editTextPasswordAgainRegister.text.toString().length >= UP_LIMIT
                    && editTextPasswordAgainRegister.text.toString() == editTextPasswordRegister.text.toString()) {
                    buttonRegister.isEnabled = true
                    buttonRegister.setBackgroundColor(resources.getColor(R.color.Before))
                } else {
                    buttonRegister.isEnabled = false
                    buttonRegister.setBackgroundColor(resources.getColor(R.color.After))
                }
            }

        })

        editTextPasswordRegister.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editTextEmail.text.toString().isNotEmpty() && editTextPasswordRegister.text.toString().length >= UP_LIMIT
                    && isValidEmail(editTextEmail.text.toString()) && editTextPasswordAgainRegister.text.toString().length >= UP_LIMIT
                    && editTextPasswordAgainRegister.text.toString() == editTextPasswordRegister.text.toString()) {
                    buttonRegister.isEnabled = true
                    buttonRegister.setBackgroundColor(resources.getColor(R.color.Before))
                } else {
                    buttonRegister.isEnabled = false
                    buttonRegister.setBackgroundColor(resources.getColor(R.color.After))
                }
            }

        })

        editTextPasswordAgainRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editTextEmail.text.toString().isNotEmpty() && editTextPasswordRegister.text.toString().length >= UP_LIMIT
                    && isValidEmail(editTextEmail.text.toString()) && editTextPasswordAgainRegister.text.toString().length >= UP_LIMIT
                    && editTextPasswordAgainRegister.text.toString() == editTextPasswordRegister.text.toString()) {
                    buttonRegister.isEnabled = true
                    buttonRegister.setBackgroundColor(resources.getColor(R.color.Before))
                } else {
                    buttonRegister.isEnabled = false
                    buttonRegister.setBackgroundColor(resources.getColor(R.color.After))
                }
            }

        })
    }

    fun isValidEmail(string : String): Boolean {
        return string.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }


    private fun registerUser() {
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        var user = User(email = editTextEmail.text.toString(), password = editTextPasswordRegister.text.toString(),showId = "")


        viewModel.setData(user)

        viewModel.liveData.observe(this, Observer {
            if (it != null) {
                if (it.isSuccessful) {
                    userActive = user
                    val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                    intent.putExtra(EMAIL, user.email)
                    startActivity(intent)
                    finish()
                } else {
                    viewModel.resetLiveData()
                    val dialog = AlertDialog.Builder(this@RegisterActivity)
                    dialog.setTitle(it.message)
                    dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        })
    }

    override fun onBackPressed() {
        var intent = Intent(RegisterActivity@ this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}




