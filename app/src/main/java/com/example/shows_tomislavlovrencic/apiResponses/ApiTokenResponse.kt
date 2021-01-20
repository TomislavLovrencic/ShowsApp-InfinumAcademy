package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.Token
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiTokenResponse(
    @Json(name = "data")
    val apiToken: Token?,

    @Transient
    var message : String = "",
    var isSuccessful: Boolean = true
)

