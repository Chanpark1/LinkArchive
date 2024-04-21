package com.chanyoung.jack.data.room.dao

import androidx.room.*
import com.chanyoung.jack.data.model.LinkGroup

@Dao
interface LinkGroupDao {
    @Insert
    suspend fun insertGroup(group : LinkGroup)

    @Delete
    suspend fun deleteGroup(group : LinkGroup)

    @Transaction
    @Query("UPDATE LinkGroup SET name = :name WHERE gid = :gid")
    suspend fun updateGroup(name : String, gid : Int)

    @Transaction
    @Query("SELECT COUNT(*) FROM LinkGroup")
    suspend fun getGroupCount() : Int

    @Transaction
    @Query("SELECT * FROM LinkGroup WHERE gid = :gid")
    suspend fun getLinkGroup(gid : Int) : LinkGroup

    @Transaction
    @Query("SELECT * FROM LinkGroup")
    suspend fun getAllGroup() : List<LinkGroup>

    @Transaction
    @Query("SELECT * FROM LinkGroup WHERE NOT gid = :gid")
    suspend fun getAllGroupExcept(gid : Int) : List<LinkGroup>

    @Transaction
    @Query("SELECT COUNT(*) From LinkGroup WHERE name = :groupName")
    suspend fun checkDuplicateGroup(groupName : String) : Int

    @Transaction
    @Query("SELECT gid FROM LinkGroup WHERE name = :groupName")
    suspend fun getGroupId(groupName : String) : Int

    @Transaction
    @Query("SELECT * FROM LinkGroup ORDER BY gid DESC LIMIT :loadSize OFFSET :index * :loadSize")
    suspend fun getPaginatedGroups(index : Int, loadSize : Int) : List<LinkGroup>

    @Transaction
    @Query("UPDATE LinkGroup SET linkCount = (SELECT COUNT(*) FROM Link WHERE gid = :gid) WHERE gid = :gid")
    suspend fun updateLinkCount(gid : Int)


}