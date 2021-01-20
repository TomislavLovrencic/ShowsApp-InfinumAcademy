package com.example.shows_tomislavlovrencic.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ApiShow (
    @Json(name = "type")
    var type: String?,

    @Json(name = "title")
    var title: String?,

    @Json(name = "description")
    var description: String?,

    @Json(name = "_id")
    var id : String?,

    @Json(name = "likesCount")
    var likes: Int,

    @Json(name = "imageUrl")
    var imageUrl: String?
)