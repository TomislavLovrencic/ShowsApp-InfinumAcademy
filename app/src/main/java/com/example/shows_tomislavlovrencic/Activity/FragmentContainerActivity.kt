package com.example.shows_tomislavlovrencic.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shows_tomislavlovrencic.Fragments.EpisodesFragment
import com.example.shows_tomislavlovrencic.Fragments.ShowsListFragment
import com.example.shows_tomislavlovrencic.R
import kotlinx.android.synthetic.main.activity_fragment_container.*


var twoPane: Boolean = false

class FragmentContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        sharedPrefs = getSharedPreferences("login", Context.MODE_PRIVATE)



        twoPane =
            detailsFragmentContainer != null && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (twoPane) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, ShowsListFragment())
                replace(R.id.detailsFragmentContainer, EpisodesFragment())
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                //if (supportFragmentManager.backStackEntryCount == 0) {
                    add(R.id.fragmentContainer, ShowsListFragment(),"shows-tagged")
                    addToBackStack("shows-tagged")
                    commit()
                //}
            }

        }
    }


    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.findFragmentByTag("episode-details") != null) {
            fragmentManager.popBackStackImmediate()

        } else {
            super.onBackPressed()
            android.os.Process.killProcess(android.os.Process.myPid())

        }
    }

}