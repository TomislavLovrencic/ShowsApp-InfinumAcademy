package com.example.shows_tomislavlovrencic.Repositories

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.shows_tomislavlovrencic.Activity.INTERNET_CONNECTION
import com.example.shows_tomislavlovrencic.Activity.WENT_WRONG
import com.example.shows_tomislavlovrencic.Activity.mProgressDialog
import com.example.shows_tomislavlovrencic.Api
import com.example.shows_tomislavlovrencic.Fragments.emailString
import com.example.shows_tomislavlovrencic.Fragments.showIdResult
import com.example.shows_tomislavlovrencic.RetrofitClient
import com.example.shows_tomislavlovrencic.ShowsApp
import com.example.shows_tomislavlovrencic.apiResponses.ApiResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiShowResponse
import com.example.shows_tomislavlovrencic.apiResponses.ShowLikeResponse
import com.example.shows_tomislavlovrencic.classes.Show
import com.example.shows_tomislavlovrencic.dao.ShowsDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*var listaShows: MutableList<Show> = listOf(
    Show(
        "Chuck",
        "(2007 - 2012)",
        R.drawable.chuck,
        "When a twenty-something computer geek inadvertently downloads critical government secrets into his brain, the C.I.A. and the N.S.A.",
        "100"
    ),
    Show(
        "Game of Thrones",
        "(2011 - 2019)",
        R.drawable.got,
        "Nine noble families fight for control over the mythical lands of Westeros, while an ancient enemy returns after being dormant for thousands of years.",
        "101"
    ),
    Show(
        "Dexter",
        "(2006 - 2013)",
        R.drawable.dexter,
        "By day, mild-mannered Dexter is a blood-spatter analyst for the Miami police. But at night, he is a serial killer who only targets other murderers.",
        "102"
    ),
    Show(
        "Chernobyl",
        "(2019 - 2019)",
        R.drawable.chernobyl,
        "In April 1986, an explosion at the Chernobyl nuclear power plant in the Union of Soviet Socialist Republics becomes one of the world's worst man-made catastrophes.",
        "103"
    ),
    Show(
        "Video game high school",
        "(2012 - 2014)",
        R.drawable.vghs,
        "In a futuristic world where gaming is the top sport, a teenager attends a school which specializes in a curriculum of video games in each genre.",
        "104"
    )
).toMutableList()
*/



object RepositoryShows {

    private val database: ShowsDatabase = Room.databaseBuilder(
        ShowsApp.instance,
        ShowsDatabase::class.java, "shows-database"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    fun getLikeStatus(showId: String) : Int = database.showsDao().getLikeStatus(showId, emailString!!)

    fun insertLike(show:Show) = database.showsDao().insertLike(show)

    fun updateLikeStatus(show:Show) = database.showsDao().updateLikeStatus(show.ShowId!!, emailString!!,show.like)





    private val likeResponseLiveData = MutableLiveData<ShowLikeResponse>()


    fun liveLikeData() : LiveData<ShowLikeResponse> {
        return likeResponseLiveData
    }



    private val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)

    private val showResponseLiveData = MutableLiveData<ApiResponse>()

    private val showData = MutableLiveData<ApiShowResponse>()

    fun liveData2() : LiveData<ApiShowResponse> {
        return showData
    }

    fun liveData(): LiveData<ApiResponse> {
        return showResponseLiveData
    }


    fun fetchData() {
        apiService?.getShows()?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)
                    }
                    val shows = response.body()!!.apiShowList
                    showResponseLiveData.value = ApiResponse(shows,isSuccessful = true,message = "")
                }
                else{
                    showResponseLiveData.value = ApiResponse(isSuccessful = false,message = WENT_WRONG,apiShowList = null)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, throwable: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                showResponseLiveData.value = ApiResponse(isSuccessful = false,message = INTERNET_CONNECTION,apiShowList = null)
            }

        })
    }

    fun fetchData2(){
        val apiService2 = RetrofitClient.retrofitInstance?.create(Api::class.java)

        apiService2?.getShow(showIdResult!!)?.enqueue(object  : Callback<ApiShowResponse> {
            override fun onFailure(call: Call<ApiShowResponse>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                showData.value = ApiShowResponse(isSuccessful = false,message = INTERNET_CONNECTION,apiShow = null)

            }

            override fun onResponse(call: Call<ApiShowResponse>, response: Response<ApiShowResponse>) {
                if(response.isSuccessful){
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)
                    }
                    val apiShow = response.body()?.apiShow
                    showData.value = ApiShowResponse(apiShow!!,isSuccessful = true,message = "")
                    //textViewShowName.text = apiShow!!.title
                    //textViewShowDescription.text = apiShow.description
                }
                else{
                    showData.value = ApiShowResponse(isSuccessful = false,message = WENT_WRONG,apiShow = null)
                }
            }

        })
    }

    fun likeShow(tokenString: String,showId : String){
        val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)
        apiService?.likeShow(tokenString,showId)?.enqueue(object : retrofit2.Callback<ShowLikeResponse> {
            override fun onResponse(call: Call<ShowLikeResponse>, response: Response<ShowLikeResponse>) {
                if (response.isSuccessful) {

                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    likeResponseLiveData.value = ShowLikeResponse(type = "1", isSuccessful = true,message = "")
                    fetchData2()
                    val status = getLikeStatus(showId)
                    if (status == 0) {
                        insertLike(Show(userName = emailString!!, ShowId = showId, like = 1))
                    } else if (status == 2) {
                        updateLikeStatus(Show(userName = emailString!!, ShowId = showId, like = 1))
                    }
                }
                else{
                    likeResponseLiveData.value = ShowLikeResponse(type = "", isSuccessful = false,message = WENT_WRONG)
                }
            }

            override fun onFailure(call: Call<ShowLikeResponse>, throwable: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                likeResponseLiveData.value = ShowLikeResponse(type = "", isSuccessful = false,message = INTERNET_CONNECTION)
            }

        })
    }

    fun dislikeShow(tokenString: String,showId : String){
        val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)
        apiService?.dislikeShow(tokenString,showId)?.enqueue(object : retrofit2.Callback<ShowLikeResponse> {
            override fun onResponse(call: Call<ShowLikeResponse>, response: Response<ShowLikeResponse>) {
                if(response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    likeResponseLiveData.value = ShowLikeResponse(type = "2", isSuccessful = true,message = "")
                    fetchData2()
                    val status = getLikeStatus(showId)
                    if (status == 0) {
                        insertLike(Show(userName = emailString!!, ShowId = showId, like = 2))
                    } else if (status == 1) {
                        updateLikeStatus(Show(userName = emailString!!, ShowId = showId, like = 2))
                    }
                }
                else{
                    likeResponseLiveData.value = ShowLikeResponse(type = "", isSuccessful = false,message = WENT_WRONG)
                }
            }
            override fun onFailure(call: Call<ShowLikeResponse>, throwable: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                likeResponseLiveData.value = ShowLikeResponse(type = "", isSuccessful = false,message = INTERNET_CONNECTION)
            }

        })
    }

}