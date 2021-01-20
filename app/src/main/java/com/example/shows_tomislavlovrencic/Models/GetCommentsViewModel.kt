package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryComments
import com.example.shows_tomislavlovrencic.apiResponses.ApiGetComments

class GetCommentsViewModel : ViewModel(), Observer<ApiGetComments> {


    private val getCommentsLiveData = MutableLiveData<ApiGetComments>()


    val liveData: LiveData<ApiGetComments>
        get() {
            return getCommentsLiveData
        }


    init {
        RepositoryComments.liveData().observeForever(this)
    }

    fun getComments(episodeId : String){
        RepositoryComments.getComments(episodeId)
    }

    override fun onCleared() {
        RepositoryComments.liveData().removeObserver(this)
    }

    fun resetLiveData(){
        getCommentsLiveData.value = null
    }

    override fun onChanged(t: ApiGetComments?) {
        getCommentsLiveData.value = t
    }



}