package com.example.shows_tomislavlovrencic.Repositories

import android.annotation.SuppressLint
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shows_tomislavlovrencic.Activity.INTERNET_CONNECTION
import com.example.shows_tomislavlovrencic.Activity.WENT_WRONG
import com.example.shows_tomislavlovrencic.Activity.mProgressDialog
import com.example.shows_tomislavlovrencic.Activity.sharedPrefs
import com.example.shows_tomislavlovrencic.Activity.sharedPrefsEditor
import com.example.shows_tomislavlovrencic.Api
import com.example.shows_tomislavlovrencic.RetrofitClient
import com.example.shows_tomislavlovrencic.apiResponses.ApiRegisteredUserResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiTokenResponse
import com.example.shows_tomislavlovrencic.classes.Token
import com.example.shows_tomislavlovrencic.classes.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RepositoryUser {




    private var tokenResponseLiveData = MutableLiveData<ApiTokenResponse?>()

    val LiveTokenData: LiveData<ApiTokenResponse?>?
        get() = tokenResponseLiveData


    private val registerResponseLiveData = MutableLiveData<ApiRegisteredUserResponse>()
    val LiveRegisterData: LiveData<ApiRegisteredUserResponse>
        get() = registerResponseLiveData

    /*init {

        val loginToken =
            ShowsApp.instance?.getSharedPreferences("login", Context.MODE_PRIVATE)?.getString("token", "") ?: ""
        if (loginToken.isNotEmpty()) {
            tokenResponseLiveData.value = ApiTokenResponse(Token(loginToken), isSuccessful = true)
        }
    }*/



    fun setBoolean(){
        sharedPrefsEditor = sharedPrefs.edit()
        sharedPrefsEditor.putBoolean("checkbox",false)
        sharedPrefsEditor.apply()
    }

    fun login(user: User,rememberMe:Boolean) {

        val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)

        apiService?.loginUser(user)?.enqueue(object : Callback<ApiTokenResponse> {
            override fun onFailure(call: Call<ApiTokenResponse>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                tokenResponseLiveData.value =
                    ApiTokenResponse(Token(), isSuccessful = false,message = INTERNET_CONNECTION)

            }
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(call: Call<ApiTokenResponse>, response: Response<ApiTokenResponse>) {
                val loginToken = Token(response.body()?.apiToken?.tokenString)

                if (response.isSuccessful && loginToken.tokenString != null) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)

                    }
                    if (rememberMe) {
                        sharedPrefsEditor = sharedPrefs.edit()
                        sharedPrefsEditor.putBoolean("checkbox",true)
                        sharedPrefsEditor.putString("email",user.email)
                        sharedPrefsEditor.apply()

                    }
                    sharedPrefsEditor = sharedPrefs.edit()
                    sharedPrefsEditor.putString("email",user.email)
                    sharedPrefsEditor.putString("token",response.body()?.apiToken?.tokenString)
                    sharedPrefsEditor.apply()


                    tokenResponseLiveData.value =
                        ApiTokenResponse(Token(response.body()!!.apiToken?.tokenString), isSuccessful = true,message = "")

                }
                else{
                    tokenResponseLiveData.value =
                        ApiTokenResponse(isSuccessful = false,message = WENT_WRONG,apiToken = null)
                }

            }
        })
    }


    fun register(user: User) {

        val apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)

        apiService?.registerUser(user)?.enqueue(object : Callback<ApiRegisteredUserResponse> {
            override fun onFailure(call: Call<ApiRegisteredUserResponse>, t: Throwable) {
                mProgressDialog!!.isIndeterminate = true
                mProgressDialog!!.setMessage("Loading...")
                mProgressDialog!!.show()
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 1000)
                }
                registerResponseLiveData.value =
                    ApiRegisteredUserResponse(isSuccessful = false,message = INTERNET_CONNECTION,apiRegisteredUser = null)


            }

            override fun onResponse(call: Call<ApiRegisteredUserResponse>, response: Response<ApiRegisteredUserResponse>) {
                if(response.isSuccessful) {
                    mProgressDialog!!.isIndeterminate = true
                    mProgressDialog!!.setMessage("Loading...")
                    mProgressDialog!!.show()
                    if (mProgressDialog?.isShowing!!) {
                        Handler().postDelayed({
                            mProgressDialog?.dismiss()
                        }, 1000)
                    }
                    registerResponseLiveData.value =
                        ApiRegisteredUserResponse(response.body()?.apiRegisteredUser!!, isSuccessful = true,message = "")
                }
                else{
                    registerResponseLiveData.value =
                        ApiRegisteredUserResponse(isSuccessful = false,message = WENT_WRONG,apiRegisteredUser = null)
                }
            }
        })
    }

}