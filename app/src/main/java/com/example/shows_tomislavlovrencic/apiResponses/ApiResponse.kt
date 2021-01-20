package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.ApiShow
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "data")
    val apiShowList: List<ApiShow>?,

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true
)