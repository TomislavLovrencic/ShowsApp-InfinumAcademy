package com.example.shows_tomislavlovrencic.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Media {

    @Json(name = "path")
    var path: String? = null

    @Json(name = "type")
    var type: String? = null

    @Json(name = "_id")
    var id: String? = null
}