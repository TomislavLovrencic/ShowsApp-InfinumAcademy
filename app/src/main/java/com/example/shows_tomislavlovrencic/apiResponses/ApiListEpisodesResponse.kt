package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.EpisodeApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiListEpisodesResponse(
    @Json(name = "data")
    val apiEpisodesList: List<EpisodeApi>?,

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true
)