package com.example.shows_tomislavlovrencic.Activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.shows_tomislavlovrencic.Models.SharedPrefViewModel
import com.example.shows_tomislavlovrencic.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    private var homeButton: Boolean = true

    private lateinit var viewModel: SharedPrefViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPrefs = getSharedPreferences("login", Context.MODE_PRIVATE)

        startBouncingAnimation()

    }


    private fun startTextAnimation() {
        val animator = ValueAnimator.ofFloat(1f, 40f)
        animator.duration = 1000

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            showsss!!.textSize = animatedValue
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                startLoading()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        showsss!!.setTextColor(Color.BLACK)
        animator.start()

    }


    private fun startBouncingAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce_transition)
        imagesss!!.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                startTextAnimation()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }


    private fun startLoading() {
        Handler().postDelayed({ checkIfRememberMeAccExists() }, 1000)
    }

    private fun checkIfRememberMeAccExists() {

        homeButton = false

        viewModel = ViewModelProviders.of(this).get(SharedPrefViewModel::class.java)


        val rememberMeBoolean: Boolean = viewModel.getRemember()!!

        if (rememberMeBoolean) {
            val intent = Intent(this, FragmentContainerActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        android.os.Process.killProcess(android.os.Process.myPid())
    }


    override fun onStop() {
        super.onStop()
        if (homeButton) {
            onBackPressed()
        }
    }
}