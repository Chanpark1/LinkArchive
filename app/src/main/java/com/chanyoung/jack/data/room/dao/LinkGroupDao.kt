package com.chanyoung.jack.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.chanyoung.jack.data.model.LinkGroup

@Dao
interface LinkGroupDao {
    @Insert
    suspend fun insertGroup(group : LinkGroup)

    @Transaction
    @Query("SELECT COUNT(*) FROM LinkGroup")
    suspend fun getGroupCount() : Int

    @Transaction
    @Query("SELECT * FROM LinkGroup")
    suspend fun getAllGroup() : List<LinkGroup>
}