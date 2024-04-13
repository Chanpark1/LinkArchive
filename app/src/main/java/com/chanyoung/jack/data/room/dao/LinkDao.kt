package com.chanyoung.jack.data.room.dao

import androidx.room.*
import com.chanyoung.jack.data.model.Link

@Dao
interface LinkDao {

    @Insert
    suspend fun insertLink(link : Link) : Long

    @Transaction
    @Query("SELECT * FROM Link ORDER BY lid DESC")
    suspend fun getAllLinks() : List<Link>

    @Transaction
    @Query("SELECT * FROM Link ORDER BY lid DESC LIMIT :loadSize OFFSET :index * :loadSize")
    suspend fun getPaginatedLinks(index : Int, loadSize : Int): List<Link>

    @Transaction
    @Query("SELECT * FROM Link WHERE lid = :lid")
    suspend fun getLink(lid : Int) : Link
}