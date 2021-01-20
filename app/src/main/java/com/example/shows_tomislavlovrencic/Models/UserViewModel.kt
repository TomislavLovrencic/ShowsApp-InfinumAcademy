package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryUser
import com.example.shows_tomislavlovrencic.apiResponses.ApiRegisteredUserResponse
import com.example.shows_tomislavlovrencic.classes.User

class UserViewModel : ViewModel(), Observer<ApiRegisteredUserResponse> {


    init {
        RepositoryUser.LiveRegisterData.observeForever(this)
    }

    private val userResponseData = MutableLiveData<ApiRegisteredUserResponse>()

    val liveData: LiveData<ApiRegisteredUserResponse>
        get() {
            return userResponseData
        }

    fun setData(user: User) {
        RepositoryUser.register(user)
    }

    override fun onCleared() {
        RepositoryUser.LiveRegisterData.removeObserver(this)
    }

    override fun onChanged(apiResponse: ApiRegisteredUserResponse?) {
        userResponseData.value = apiResponse
    }

    fun resetLiveData(){
        userResponseData.value = null

    }

}