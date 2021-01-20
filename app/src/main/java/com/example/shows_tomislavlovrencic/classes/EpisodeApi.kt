package com.example.shows_tomislavlovrencic.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable


@JsonClass(generateAdapter = true)
class EpisodeApi  (

    @Json(name = "showId")
    var showId: String = "dksad329d2i",

    @Json(name = "mediaId")
    var mediaId: String? = "dai9di329ud",

    @Json(name = "title")
    var title: String? = "ekipsa",

    @Json(name = "description")
    var description: String? = "osadoasdk",

    @Json(name = "episodeNumber")
    var episodeNmb: String? = "3",

    @Json(name = "season")
    var season: String? = "5",

    @Json(name = "_id")
    var episodeId : String?,

    @Json(name = "imageUrl")
    var imageUrl : String?

) : Serializable