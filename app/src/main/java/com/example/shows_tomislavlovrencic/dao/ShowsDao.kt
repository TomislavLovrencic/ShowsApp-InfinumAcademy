package com.example.shows_tomislavlovrencic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shows_tomislavlovrencic.classes.Show

@Dao
interface ShowsDao {
    @Query("Select `like` from Show where id_show = :showId and user = :user")
    fun getLikeStatus(showId : String,user: String) : Int

    @Insert
    fun insertLike(show : Show)

    @Query("Update Show set `like`=:like where id_show=:showId and user=:user")
    fun updateLikeStatus(showId: String,user: String,like:Int)

}