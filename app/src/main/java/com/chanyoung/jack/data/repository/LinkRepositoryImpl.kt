package com.chanyoung.jack.data.repository

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.basic.LinkRepository
import com.chanyoung.jack.data.room.dao.LinkDao
import com.chanyoung.jack.data.room.dao.LinkGroupDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepositoryImpl @Inject constructor(
    private val linkDao: LinkDao,
    private val groupDao : LinkGroupDao
) : LinkRepository {
    override suspend fun insertLink(link: Link) {
        CoroutineScope(Dispatchers.IO).launch {
            linkDao.insertLink(link)
            groupDao.updateLinkCount(link.groupId)
        }
    }

    override suspend fun getAllLinks(): List<Link> {
        return withContext(Dispatchers.IO) {
            linkDao.getAllLinks()
        }
    }

    override suspend fun getLink(lid: Int): Link {
        return withContext(Dispatchers.IO) {
            linkDao.getLink(lid)
        }
    }

    override suspend fun getLinksInGroup(gid: Int): List<Link> {
        return withContext(Dispatchers.IO) {
            linkDao.getLinksInGroup(gid)
        }
    }
    override suspend fun getPaginatedLinks(index : Int, loadSize : Int): List<Link> {
        return withContext(Dispatchers.IO) {
            linkDao.getPaginatedLinks(index, loadSize)
        }
    }

    override suspend fun getPaginatedLinksByGroupId(index: Int, loadSize: Int, gid: Int): List<Link> {
        return withContext(Dispatchers.IO) {
            linkDao.getPaginatedLinksByGroupId(index, loadSize, gid)
        }
    }

    override suspend fun deleteLink(link : Link) {
       return withContext(Dispatchers.IO) {
           linkDao.deleteLink(link)
           groupDao.updateLinkCount(link.groupId)
       }
    }

    override suspend fun relocateLink(lid: Int, oldGid: Int, newGid : Int) {
        return withContext(Dispatchers.IO) {
            linkDao.relocateLink(lid, newGid)
            groupDao.updateLinkCount(oldGid)
            groupDao.updateLinkCount(newGid)
        }
    }

    override suspend fun updateLink(
        lid: Int,
        title: String,
        url: String,
        memo: String,
        image_path: String?
    ) {
        return withContext(Dispatchers.IO) {
            linkDao.updateLink(lid, title, url, memo, image_path)
        }
    }

    override suspend fun searchLinkByGroupId(gid: Int, query: String) : List<Link> {
        return withContext(Dispatchers.IO) {
            linkDao.searchLinkByGroupId(gid, query)
        }
    }
}