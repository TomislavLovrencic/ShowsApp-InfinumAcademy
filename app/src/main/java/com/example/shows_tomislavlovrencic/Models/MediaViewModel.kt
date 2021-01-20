package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryEpisodes
import com.example.shows_tomislavlovrencic.apiResponses.ApiMediaResponse
import okhttp3.RequestBody

class MediaViewModel : ViewModel(), Observer<ApiMediaResponse> {


    private val mediaLiveData = MutableLiveData<ApiMediaResponse>()


    val liveData: LiveData<ApiMediaResponse>
        get() {
            return mediaLiveData
        }


    init {
        RepositoryEpisodes.liveMediaData().observeForever(this)
    }


    override fun onCleared() {
        RepositoryEpisodes.liveMediaData().removeObserver(this)
    }

    fun resetLiveData(){
        mediaLiveData.value = null
    }

    fun addMedia(token: String,requestBody: RequestBody){
        RepositoryEpisodes.addMedia(token,requestBody)
    }


    override fun onChanged(t: ApiMediaResponse?) {
        mediaLiveData.value = t
    }

}