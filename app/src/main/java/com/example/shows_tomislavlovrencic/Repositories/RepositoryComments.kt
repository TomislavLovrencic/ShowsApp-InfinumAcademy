package com.example.shows_tomislavlovrencic.Repositories

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shows_tomislavlovrencic.Activity.INTERNET_CONNECTION
import com.example.shows_tomislavlovrencic.Activity.WENT_WRONG
import com.example.shows_tomislavlovrencic.Activity.mProgressDialog
import com.example.shows_tomislavlovrencic.Api
import com.example.shows_tomislavlovrencic.RetrofitClient
import com.example.shows_tomislavlovrencic.apiResponses.ApiGetComments
import com.example.shows_tomislavlovrencic.apiResponses.ApiPostComment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RepositoryComments {


    private val commentsResponseLiveData = MutableLiveData<ApiGetComments>()

    private val postCommentsResponseLiveData = MutableLiveData<ApiPostComment>()

    fun livePostData() : LiveData<ApiPostComment> {
        return postCommentsResponseLiveData
    }

    fun liveData() : LiveData<ApiGetComments> {
        return commentsResponseLiveData
    }


    fun getComments(episodeId : String){
         val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)
        val getComment = apiService?.getComments(episodeId)
        getComment?.enqueue(object : Callback<ApiGetComments> {
            override fun onFailure(call: Call<ApiGetComments>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                commentsResponseLiveData.value =
                    ApiGetComments(isSuccessful = false,message = INTERNET_CONNECTION,apiGetComments = null)
            }

            override fun onResponse(call: Call<ApiGetComments>, response: Response<ApiGetComments>) {
                if(response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    commentsResponseLiveData.value =
                        ApiGetComments(response.body()?.apiGetComments!!, isSuccessful = true,message = "")
                }
                else{
                    commentsResponseLiveData.value =
                        ApiGetComments(response.body()?.apiGetComments!!, isSuccessful = false,message = WENT_WRONG)
                }
            }

        })

    }

    fun postComment(tokenString: String,comment: com.example.shows_tomislavlovrencic.classes.Comment){

        val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)
        val postComment = apiService?.postComment(tokenString,comment)
        postComment?.enqueue(object : Callback<ApiPostComment> {
            override fun onFailure(call: Call<ApiPostComment>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                postCommentsResponseLiveData.value = ApiPostComment(isSuccessful = false,message = INTERNET_CONNECTION,apiPostComment = null)
            }

            override fun onResponse(call: Call<ApiPostComment>, response: Response<ApiPostComment>) {
                if(response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    postCommentsResponseLiveData.value =
                        ApiPostComment(response.body()?.apiPostComment!!, isSuccessful = true,message = "")
                    getComments(response.body()!!.apiPostComment?.episodeId!!)
                }
                else{
                    postCommentsResponseLiveData.value = ApiPostComment(response.body()?.apiPostComment!!, isSuccessful = false,message = WENT_WRONG)
                }
            }

        })
    }


}