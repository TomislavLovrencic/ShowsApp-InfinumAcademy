package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Activity.sharedPrefs
import com.example.shows_tomislavlovrencic.Repositories.RepositoryShows
import com.example.shows_tomislavlovrencic.apiResponses.ApiResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiShowResponse

class ShowsViewModel : ViewModel(), Observer<ApiResponse> {


    private val showResponseData = MutableLiveData<ApiResponse>()


    val liveData: LiveData<ApiResponse>
        get() = showResponseData


    init {
        RepositoryShows.liveData().observeForever(this)
    }

    fun getData() {
        RepositoryShows.fetchData()
    }

    fun getEmail(): String? {
        return sharedPrefs.getString("email","")
    }

    fun getToken(): String? {
        return sharedPrefs.getString("token","")
    }

    fun resetLiveData(){
        showResponseData.value = null
    }

    override fun onCleared() {
        RepositoryShows.liveData().removeObserver(this)
    }

    override fun onChanged(apiResponse: ApiResponse?) {
        showResponseData.value = apiResponse
    }

}