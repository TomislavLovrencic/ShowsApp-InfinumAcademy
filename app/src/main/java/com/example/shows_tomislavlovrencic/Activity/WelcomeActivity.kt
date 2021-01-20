package com.example.shows_tomislavlovrencic.Activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.shows_tomislavlovrencic.R
import kotlinx.android.synthetic.main.activity_welcome.*


const val EMAIL = "email"
var prolaz : Boolean = true

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val string = intent.getStringExtra(EMAIL)
        val seperated = string.split("@")

        textViewWelcome.text = "Welcome ${seperated.first()}"


        Handler().postDelayed({
            if(prolaz) {
                intent = Intent(this, FragmentContainerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }

    override fun onBackPressed() {
        prolaz=false
        finishAffinity()
    }


}