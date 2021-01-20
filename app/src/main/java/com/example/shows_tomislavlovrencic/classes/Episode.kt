package com.example.shows_tomislavlovrencic.classes

import android.graphics.Bitmap

class Episode constructor(name: String,season : String ,episode : String,showId : String) {

    var description : String = ""
    var photo : Bitmap? = null
    var showId :String?
    var name: String = ""
    var season : String= "1"
    var episode : String = "1"

    init {
        this.description = description
        this.photo = photo
        this.showId = showId
        this.season = season
        this.episode =episode
        this.name = name
    }
}