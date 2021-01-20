package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.Comment
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiGetComments(
    @Json(name = "data")
    val apiGetComments: List<Comment>?,

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true
)