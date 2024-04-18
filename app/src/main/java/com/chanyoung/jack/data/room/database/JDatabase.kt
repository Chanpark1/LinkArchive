package com.chanyoung.jack.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.model.GroupLinkCrossRef
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.room.dao.LinkDao
import com.chanyoung.jack.data.room.dao.LinkGroupDao

@Database (
    version = 8,
    entities = [
        Link::class,
        LinkGroup::class,
        GroupLinkCrossRef::class
    ],
    exportSchema = false
        )

abstract class JDatabase : RoomDatabase() {
    abstract fun getLinkDao() : LinkDao
    abstract fun getGroupDao() : LinkGroupDao
}