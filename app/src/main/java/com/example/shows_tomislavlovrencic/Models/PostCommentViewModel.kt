package com.example.shows_tomislavlovrencic.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.shows_tomislavlovrencic.Repositories.RepositoryComments
import com.example.shows_tomislavlovrencic.apiResponses.ApiPostComment
import com.example.shows_tomislavlovrencic.classes.Comment

class PostCommentViewModel : ViewModel(), Observer<ApiPostComment> {


    private val postCommentsLiveData = MutableLiveData<ApiPostComment>()


    val liveData: LiveData<ApiPostComment>
        get() {
            return postCommentsLiveData
        }


    init {
        RepositoryComments.livePostData().observeForever(this)
    }


    override fun onCleared() {
        RepositoryComments.livePostData().removeObserver(this)
    }

    fun postComment(tokenString: String,comment: Comment){
        RepositoryComments.postComment(tokenString,comment)
    }

    fun resetLiveData(){
        postCommentsLiveData.value = null
    }


    override fun onChanged(t: ApiPostComment?) {
        postCommentsLiveData.value = t
    }

}