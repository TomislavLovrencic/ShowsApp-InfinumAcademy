package com.example.shows_tomislavlovrencic.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json

@Entity(primaryKeys = ["email", "showId"])
class User (

    @Json(name = "email")
    @ColumnInfo(name = "email")
    var email: String,

    @Json(name = "password")
    var password: String? = null,

    @ColumnInfo(name = "showId")
    var showId: String,

    @ColumnInfo(name = "showStatus")
    var showStatus: String? = null,

    var userToken : String? = null
)