package com.example.shows_tomislavlovrencic.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Token(

    @Json(name = "token")
    var tokenString: String? = null

) : Serializable