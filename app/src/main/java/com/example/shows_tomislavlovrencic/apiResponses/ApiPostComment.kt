package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.Comment
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPostComment(
    @Json(name = "data")
    val apiPostComment: Comment?,

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true
)