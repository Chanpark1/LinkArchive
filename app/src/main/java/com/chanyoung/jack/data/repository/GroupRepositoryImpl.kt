package com.chanyoung.jack.data.repository

import android.util.Log
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.basic.GroupRepository
import com.chanyoung.jack.data.room.dao.LinkGroupDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor(
    private val dao: LinkGroupDao
) : GroupRepository {

    companion object {
        private const val DEFAULT_GROUP_NAME = "New Group"
        private const val DEFAULT_GROUP_ID = 0
    }

    override suspend fun insertGroup(group: LinkGroup) {
        withContext(Dispatchers.IO) {
            dao.insertGroup(group)
        }
    }

    override suspend fun getAllGroup(): List<LinkGroup> {
        return withContext(Dispatchers.IO) {
            dao.getAllGroup()
        }
    }

    override suspend fun hasUserCreatedGroup(): Boolean {
        return withContext(Dispatchers.IO) {
            dao.getGroupCount() > 0
        }

    }

    override suspend fun getGroupCount() {
        Log.d("getGroupCount", dao.getGroupCount().toString())
    }

    override suspend fun createDefaultGroup() {
        if (!hasUserCreatedGroup()) {
            val defaultGroup = LinkGroup(DEFAULT_GROUP_ID, DEFAULT_GROUP_NAME)
            insertGroup(defaultGroup)
            Log.d("GROUP REPO", "DEFAULT INSERT GROUP CALLED")
        }
    }

}