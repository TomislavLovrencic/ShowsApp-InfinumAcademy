package com.example.shows_tomislavlovrencic



import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {

   const val BASE_URL = "https://api.infinum.academy/"

    private var retrofit: Retrofit? = null

    val retrofitInstance : Retrofit?
        get() {
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(createOkHttpClient())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return retrofit
        }
}

private fun createOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor( ChuckInterceptor(ShowsApp.instance))
        .build()
}