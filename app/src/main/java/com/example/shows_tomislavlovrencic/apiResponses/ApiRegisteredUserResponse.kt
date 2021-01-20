package com.example.shows_tomislavlovrencic.apiResponses

import com.example.shows_tomislavlovrencic.classes.RegisteredUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRegisteredUserResponse(
    @Json(name = "data")
    val apiRegisteredUser: RegisteredUser?,

    @Transient
    var message: String = "",
    var isSuccessful: Boolean = true
)