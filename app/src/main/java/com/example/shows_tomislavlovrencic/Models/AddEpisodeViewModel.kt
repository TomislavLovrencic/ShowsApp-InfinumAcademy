package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Activity.sharedPrefs
import com.example.shows_tomislavlovrencic.Repositories.RepositoryEpisodes
import com.example.shows_tomislavlovrencic.apiResponses.ApiAddEpisodeResponse
import com.example.shows_tomislavlovrencic.classes.EpisodeApi

class AddEpisodeViewModel : ViewModel(), Observer<ApiAddEpisodeResponse> {


    private val episodeAddLiveData = MutableLiveData<ApiAddEpisodeResponse>()


    val liveData: LiveData<ApiAddEpisodeResponse>
        get() {
            return episodeAddLiveData
        }


    init {
        RepositoryEpisodes.liveAddEpisodeData().observeForever(this)
    }

    fun setData(token: String, episode: EpisodeApi) {
        RepositoryEpisodes.addEpisode(token, episode)
    }

    override fun onCleared() {
        RepositoryEpisodes.liveAddEpisodeData().removeObserver(this)
    }


    override fun onChanged(t: ApiAddEpisodeResponse?) {
        episodeAddLiveData.value = t
    }

    fun resetLiveData(){
        episodeAddLiveData.value = null
    }

    fun getToken(): String? {
        return sharedPrefs.getString("token","")
    }
}