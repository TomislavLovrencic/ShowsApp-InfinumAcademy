package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryShows
import com.example.shows_tomislavlovrencic.apiResponses.ApiShowResponse


class ShowDetailsViewModel : ViewModel(), Observer<ApiShowResponse> {

    private val showData = MutableLiveData<ApiShowResponse>()

    //private val showResponse: ApiShowResponse?
      //  get() = RepositoryShows.showData.value


    val liveData2: LiveData<ApiShowResponse>
        get() {
            return showData
        }

    init {
        RepositoryShows.liveData2().observeForever(this)
    }

    fun getData() {
        RepositoryShows.fetchData2()
    }

    fun resetLiveData(){
        showData.value = null
    }

    override fun onCleared() {
        RepositoryShows.liveData2().removeObserver(this)
    }

    override fun onChanged(api: ApiShowResponse?) {
        showData.value = api
    }

}
