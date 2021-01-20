package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.EpisodeApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiAddEpisodeResponse(
    @Json(name = "data")
    val episode : EpisodeApi?,

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true
)