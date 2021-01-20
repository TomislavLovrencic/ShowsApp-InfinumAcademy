package com.example.shows_tomislavlovrencic.apiResponses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShowLikeResponse(

    @Json(name = "data")
    var type  :String? = "0",

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true
)
