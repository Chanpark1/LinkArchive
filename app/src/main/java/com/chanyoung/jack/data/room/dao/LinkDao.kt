package com.chanyoung.jack.data.room.dao

import androidx.room.*
import com.chanyoung.jack.data.model.Link

@Dao
interface LinkDao {

    @Insert
    suspend fun insertLink(link : Link) : Long

    @Transaction
    @Query("SELECT * FROM Link")
    suspend fun getAllLinks() : List<Link>

}