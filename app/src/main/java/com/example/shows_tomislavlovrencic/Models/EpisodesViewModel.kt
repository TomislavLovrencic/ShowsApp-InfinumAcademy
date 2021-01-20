package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Activity.sharedPrefs
import com.example.shows_tomislavlovrencic.Repositories.RepositoryEpisodes
import com.example.shows_tomislavlovrencic.apiResponses.ApiListEpisodesResponse

class EpisodesViewModel : ViewModel(), Observer<ApiListEpisodesResponse> {


    private val episodesResponseLiveData = MutableLiveData<ApiListEpisodesResponse>()

    private val episodesResponse: ApiListEpisodesResponse?
        get() = episodesResponseLiveData.value

    val liveData: LiveData<ApiListEpisodesResponse>
        get() {
            return episodesResponseLiveData
        }

    init {
        RepositoryEpisodes.liveData().observeForever(this)
    }

    fun getData() {
        RepositoryEpisodes.fetchData()
    }

    override fun onCleared() {
        RepositoryEpisodes.liveData().removeObserver(this)
    }

    fun getToken(): String? {
        return sharedPrefs.getString("token","")
    }

    fun getEmail(): String? {
        return sharedPrefs.getString("email","")
    }

    fun resetLiveData(){
        episodesResponseLiveData.value = null
    }

    override fun onChanged(t: ApiListEpisodesResponse?) {
        episodesResponseLiveData.value = t
    }
}