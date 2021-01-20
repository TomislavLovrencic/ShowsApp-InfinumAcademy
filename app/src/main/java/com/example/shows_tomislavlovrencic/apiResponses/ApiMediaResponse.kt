package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.Media
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMediaResponse(

    @Json(name = "data")
    val apiAddMedia: Media?,

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true

)