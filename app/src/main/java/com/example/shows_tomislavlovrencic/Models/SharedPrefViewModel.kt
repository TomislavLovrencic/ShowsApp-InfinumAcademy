package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Activity.sharedPrefs
import com.example.shows_tomislavlovrencic.Repositories.RepositoryUser

class SharedPrefViewModel : ViewModel() {

    fun getRemember(): Boolean? {
        return sharedPrefs.getBoolean("checkbox", false)
    }

    fun getToken(): String? {
        return sharedPrefs.getString("token","")
    }

    fun getEmail(): String? {
        return sharedPrefs.getString("email","")
    }

    fun setBoolean(){
        RepositoryUser.setBoolean()
    }


}