package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryShows
import com.example.shows_tomislavlovrencic.apiResponses.ShowLikeResponse
import com.example.shows_tomislavlovrencic.classes.Show

class LikeShowViewModel : ViewModel(), Observer<ShowLikeResponse> {


    private val likeLiveData = MutableLiveData<ShowLikeResponse>()


    val liveData: LiveData<ShowLikeResponse>
        get() {
            return likeLiveData
        }


    init {
        RepositoryShows.liveLikeData().observeForever(this)
    }


    override fun onCleared() {
        RepositoryShows.liveLikeData().removeObserver(this)
    }

    fun getLikeStatus(showId: String) : Int{
        return RepositoryShows.getLikeStatus(showId)
    }

    fun insertLike(show:Show){
        RepositoryShows.insertLike(show)
    }

    fun updateLikeStatus(show : Show){
        RepositoryShows.updateLikeStatus(show)
    }

    fun resetLiveData(){
        likeLiveData.value =null
    }

    fun likeShow(tokenString: String,showId : String){
        RepositoryShows.likeShow(tokenString,showId)
    }

    fun dislikeShow(tokenString: String,showId : String){
        RepositoryShows.dislikeShow(tokenString,showId)
    }


    override fun onChanged(t: ShowLikeResponse?) {
        likeLiveData.value = t
    }

}