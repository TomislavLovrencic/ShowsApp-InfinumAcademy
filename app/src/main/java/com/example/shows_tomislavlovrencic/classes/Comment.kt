package com.example.shows_tomislavlovrencic.classes

import com.squareup.moshi.Json


class Comment (

    @Json(name = "userEmail")
    var userEmail: String?,

    @Json(name = "text")
    var text: String?,

    @Json(name = "episodeId")
    var episodeId: String
)