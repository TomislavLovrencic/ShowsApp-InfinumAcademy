package com.example.shows_tomislavlovrencic.Repositories

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shows_tomislavlovrencic.Activity.INTERNET_CONNECTION
import com.example.shows_tomislavlovrencic.Activity.WENT_WRONG
import com.example.shows_tomislavlovrencic.Activity.mProgressDialog
import com.example.shows_tomislavlovrencic.Api
import com.example.shows_tomislavlovrencic.Fragments.showIdResult
import com.example.shows_tomislavlovrencic.RetrofitClient
import com.example.shows_tomislavlovrencic.apiResponses.ApiAddEpisodeResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiEpisodeInfo
import com.example.shows_tomislavlovrencic.apiResponses.ApiListEpisodesResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiMediaResponse
import com.example.shows_tomislavlovrencic.classes.EpisodeApi
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RepositoryEpisodes {

    private val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)

    val episodeInfoLiveData = MutableLiveData<ApiEpisodeInfo>()

    val episodeResponseLiveData = MutableLiveData<ApiListEpisodesResponse>()

    val episodeAddLiveData = MutableLiveData<ApiAddEpisodeResponse>()

    val episodeMediaLiveData = MutableLiveData<ApiMediaResponse>()


    fun liveEpisodeInfoData() : LiveData<ApiEpisodeInfo> {
        return episodeInfoLiveData
    }

    fun liveMediaData(): LiveData<ApiMediaResponse> {
        return episodeMediaLiveData
    }

    fun liveAddEpisodeData(): LiveData<ApiAddEpisodeResponse> {
        return episodeAddLiveData
    }


    fun liveData(): LiveData<ApiListEpisodesResponse> {
        return episodeResponseLiveData
    }


    fun fetchData() {
        apiService?.getEpisodes(showIdResult!!)?.enqueue(object : Callback<ApiListEpisodesResponse> {
            override fun onResponse(call: Call<ApiListEpisodesResponse>, response: Response<ApiListEpisodesResponse>) {
                if (response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    var shows = response.body()!!.apiEpisodesList
                    episodeResponseLiveData.value = ApiListEpisodesResponse(shows, isSuccessful = true,message = "")
                }
                else{
                    episodeResponseLiveData.value = ApiListEpisodesResponse(isSuccessful = false,message = WENT_WRONG,apiEpisodesList = null)
                }
            }
            override fun onFailure(call: Call<ApiListEpisodesResponse>, throwable: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                episodeResponseLiveData.value = ApiListEpisodesResponse(isSuccessful = false,message = INTERNET_CONNECTION,apiEpisodesList = null)
            }

        })
    }

    fun addEpisode(token: String, episode: EpisodeApi) {

        var apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)

        apiService?.addEpisode(token, episode)?.enqueue(object : Callback<ApiAddEpisodeResponse> {

            override fun onFailure(call: Call<ApiAddEpisodeResponse>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                episodeAddLiveData.value = ApiAddEpisodeResponse(isSuccessful = false,episode = null,message = INTERNET_CONNECTION)
            }
            override fun onResponse(call: Call<ApiAddEpisodeResponse>, response: Response<ApiAddEpisodeResponse>) {
                if (response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    episodeAddLiveData.value = ApiAddEpisodeResponse(response.body()?.episode!!, isSuccessful = true,message = "")

                }
                else{
                    episodeAddLiveData.value = ApiAddEpisodeResponse(isSuccessful = false,episode = null,message = WENT_WRONG)
                }
            }

        })
    }

    fun addMedia(token : String,requestBody: RequestBody){

        var apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)


        apiService?.addPicture(token,requestBody)?.enqueue(object : Callback<ApiMediaResponse> {

            override fun onFailure(call: Call<ApiMediaResponse>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                episodeMediaLiveData.value = ApiMediaResponse(isSuccessful = false,message = INTERNET_CONNECTION,apiAddMedia = null)
            }

            override fun onResponse(call: Call<ApiMediaResponse>, response: Response<ApiMediaResponse>) {
                if (response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    episodeMediaLiveData.value = ApiMediaResponse(response.body()?.apiAddMedia!!,isSuccessful = true,message = "")

                }
                else{
                    episodeMediaLiveData.value = ApiMediaResponse(isSuccessful = false,message = WENT_WRONG,apiAddMedia = null)
                }


            }

        })

    }


    fun getEpisodeInfo(episodeId : String ){

        var apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)

        apiService?.getEpisodeInfo(episodeId)?.enqueue(object : Callback<ApiEpisodeInfo>{
            override fun onFailure(call: Call<ApiEpisodeInfo>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                episodeInfoLiveData.value = ApiEpisodeInfo(isSuccessful = false,message = INTERNET_CONNECTION,apiEpisodeInfo = null)
            }
            override fun onResponse(call: Call<ApiEpisodeInfo>, response: Response<ApiEpisodeInfo>) {
                if(response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    episodeInfoLiveData.value = ApiEpisodeInfo(response.body()?.apiEpisodeInfo!!, isSuccessful = true,message = "")
                }
                else{
                    episodeInfoLiveData.value = ApiEpisodeInfo(isSuccessful = false,message = WENT_WRONG,apiEpisodeInfo = null)
                }
            }

        })
    }


}