package com.example.shows_tomislavlovrencic.classes

import com.squareup.moshi.Json


class RegisteredUser {
    @Json(name = "password")
    val password:String? = null

    @Json(name = "email")
    val email: String? = null

    @Json(name = "_id")
    val id: String? = null
}