package com.chanyoung.jack.data.repository

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

    private companion object {
        private const val DEFAULT_GROUP_NAME = "New Group"
        private const val DEFAULT_GROUP_ID = 0
    }

    override suspend fun insertGroup(group: LinkGroup) {
        withContext(Dispatchers.IO) {
            dao.insertGroup(group)
        }
    }

    override suspend fun getLinkGroup(gid: Int): LinkGroup {
        return withContext(Dispatchers.IO) {
            dao.getLinkGroup(gid)
        }
    }
    override suspend fun updateGroup(name : String, gid : Int) {
        return withContext(Dispatchers.IO) {
            dao.updateGroup(name, gid)
        }
    }

    override suspend fun getAllGroup(): List<LinkGroup> {
        return withContext(Dispatchers.IO) {
            dao.getAllGroup()
        }
    }

    override suspend fun getAllGroupExcept(gid: Int): List<LinkGroup> {
        return withContext(Dispatchers.IO) {
            dao.getAllGroupExcept(gid)
        }
    }

    override suspend fun deleteGroup(group: LinkGroup) {
        return withContext(Dispatchers.IO) {
            dao.deleteGroup(group)
        }
    }

    override suspend fun hasUserCreatedGroup(): Boolean {
        return withContext(Dispatchers.IO) {
            dao.getGroupCount() > 0
        }

    }

    override suspend fun getGroupCount() {
        return withContext(Dispatchers.IO) {
            dao.getGroupCount()
        }
    }

    override suspend fun createDefaultGroup() {
        if (!hasUserCreatedGroup()) {
            val defaultGroup = LinkGroup(DEFAULT_GROUP_ID, DEFAULT_GROUP_NAME)
            dao.insertGroup(defaultGroup)
        }
    }
    override suspend fun checkDuplicateGroup(groupName : String) : Int{
        return withContext(Dispatchers.IO) {
            dao.checkDuplicateGroup(groupName)
        }
    }

    override suspend fun getGroupId(groupName: String) : Int {
        return withContext(Dispatchers.IO) {
            dao.getGroupId(groupName)
        }
    }

    override suspend fun getPaginatedGroups(index: Int, loadSize: Int): List<LinkGroup> {
        return withContext(Dispatchers.IO) {
            dao.getPaginatedGroups(index, loadSize)
        }
    }


}