package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryEpisodes
import com.example.shows_tomislavlovrencic.apiResponses.ApiEpisodeInfo

class EpisodeInfoViewModel : ViewModel(), Observer<ApiEpisodeInfo> {


    private val episodesInfoLiveData = MutableLiveData<ApiEpisodeInfo>()

    private val episodesInfoResponse: ApiEpisodeInfo?
        get() = episodesInfoLiveData.value

    val liveData: LiveData<ApiEpisodeInfo>
        get() {
            return episodesInfoLiveData
        }

    init {
        RepositoryEpisodes.liveEpisodeInfoData().observeForever(this)
    }

    fun getEpisodeInfo(episodeId : String){
        RepositoryEpisodes.getEpisodeInfo(episodeId)
    }

    override fun onCleared() {
        RepositoryEpisodes.liveEpisodeInfoData().removeObserver(this)
    }

    fun resetLiveData(){
        episodesInfoLiveData.value = null
    }

    override fun onChanged(t: ApiEpisodeInfo?) {
        episodesInfoLiveData.value = t
    }
}