package com.example.shows_tomislavlovrencic


import com.example.shows_tomislavlovrencic.apiResponses.ApiAddEpisodeResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiEpisodeInfo
import com.example.shows_tomislavlovrencic.apiResponses.ApiGetComments
import com.example.shows_tomislavlovrencic.apiResponses.ApiListEpisodesResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiMediaResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiPostComment
import com.example.shows_tomislavlovrencic.apiResponses.ApiRegisteredUserResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiShowResponse
import com.example.shows_tomislavlovrencic.apiResponses.ApiTokenResponse
import com.example.shows_tomislavlovrencic.apiResponses.ShowLikeResponse
import com.example.shows_tomislavlovrencic.classes.Comment
import com.example.shows_tomislavlovrencic.classes.EpisodeApi
import com.example.shows_tomislavlovrencic.classes.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface Api {

    @GET("/api/shows")
    fun getShows(): Call<ApiResponse>

    @GET("/api/episodes/{episodeId}")
    fun getEpisodeInfo(@Path("episodeId") episodeId: String): Call<ApiEpisodeInfo>


    @GET("api/shows/{showId}")
    fun getShow(@Path("showId") showId: String) : Call<ApiShowResponse>

    @GET("/api/shows/{showId}/episodes")
    fun getEpisodes(@Path("showId") id: String): Call<ApiListEpisodesResponse>


    @POST("/api/users/sessions")
    fun loginUser(@Body user: User): Call<ApiTokenResponse>

    @POST("/api/users")
    fun registerUser(@Body user: User): Call<ApiRegisteredUserResponse>


    @POST("/api/episodes")
    fun addEpisode(@Header("Authorization") token: String, @Body episode: EpisodeApi): Call<ApiAddEpisodeResponse>

    @POST("/api/media")
    @Multipart
    fun addPicture(@Header("Authorization") token: String, @Part("file\"; filename=\"image.jpg\"") request: RequestBody): Call<ApiMediaResponse>

    @GET("/api/episodes/{episodeId}/comments")
    fun getComments(@Path("episodeId") episodeId: String): Call<ApiGetComments>

    @POST("/api/comments")
    fun postComment(@Header("Authorization") token: String, @Body comment: Comment): Call<ApiPostComment>


    @POST("/api/shows/{showId}/like")
    fun likeShow(@Header("Authorization") token: String, @Path("showId") id: String): Call<ShowLikeResponse>

    @POST("/api/shows/{showId}/dislike")
    fun dislikeShow(@Header("Authorization") token: String, @Path("showId") id: String): Call<ShowLikeResponse>

}

