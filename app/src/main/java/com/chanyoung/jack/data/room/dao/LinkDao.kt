package com.chanyoung.jack.data.room.dao

import androidx.room.*
import com.chanyoung.jack.data.model.Link

@Dao
interface LinkDao {

    @Insert
    suspend fun insertLink(link : Link) : Long

    @Delete
    suspend fun deleteLink(link : Link)

    @Transaction
    @Query("SELECT * FROM Link ORDER BY lid DESC")
    suspend fun getAllLinks() : List<Link>
    @Transaction
    @Query("SELECT COUNT(*) FROM Link WHERE gid = :gid")
    suspend fun getLinkCount(gid : Int) : Int

    @Transaction
    @Query("SELECT * FROM Link ORDER BY lid DESC LIMIT :loadSize OFFSET :index * :loadSize")
    suspend fun getPaginatedLinks(index : Int, loadSize : Int): List<Link>

    @Transaction
    @Query("SELECT * FROM Link WHERE lid = :lid")
    suspend fun getLink(lid : Int) : Link

    @Transaction
    @Query("SELECT * FROM Link Where gid = :gid")
    suspend fun getLinksInGroup(gid : Int) : List<Link>



    @Transaction
    @Query("UPDATE Link SET gid = :gid WHERE lid = :lid")
    suspend fun relocateLink(lid : Int, gid : Int)

    @Transaction
    @Query("UPDATE Link SET title = :title, url = :url, memo = :memo, imagePath = :image_path WHERE lid = :lid")
    suspend fun updateLink(lid : Int, title : String, url : String, memo : String, image_path : String?)

}