package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryUser
import com.example.shows_tomislavlovrencic.apiResponses.ApiTokenResponse
import com.example.shows_tomislavlovrencic.classes.User

class UserTokenViewModel : ViewModel(), Observer<ApiTokenResponse?> {


    private val userTokenResponseLiveData = MutableLiveData<ApiTokenResponse?>()

    val liveTokenData: LiveData<ApiTokenResponse?>
        get() {
            return userTokenResponseLiveData
        }

    init {
        RepositoryUser.LiveTokenData?.observeForever(this)
    }

    fun getData(user: User,rememberMe:Boolean) {
        RepositoryUser.login(user,rememberMe)
    }

    override fun onCleared() {
        RepositoryUser.LiveTokenData?.removeObserver(this)
    }

    override fun onChanged(apiResponse: ApiTokenResponse?) {
        userTokenResponseLiveData.value = apiResponse
    }

    fun resetLiveData(){
        userTokenResponseLiveData.value = null
    }


}