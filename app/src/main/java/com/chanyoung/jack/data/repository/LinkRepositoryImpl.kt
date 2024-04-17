package com.chanyoung.jack.data.repository

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.repository.basic.LinkRepository
import com.chanyoung.jack.data.room.dao.LinkDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepositoryImpl @Inject constructor(
    private val dao: LinkDao
) : LinkRepository {
    override suspend fun insertLink(link: Link) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertLink(link)
        }
    }

    override suspend fun getAllLinks(): List<Link> {
        return withContext(Dispatchers.IO) {
            dao.getAllLinks()
        }
    }

    override suspend fun getLink(lid: Int): Link {
        return withContext(Dispatchers.IO) {
            dao.getLink(lid)
        }
    }
    override suspend fun getPaginatedLinks(index : Int, loadSize : Int): List<Link> {
        return withContext(Dispatchers.IO) {
            dao.getPaginatedLinks(index, loadSize)
        }
    }

    override suspend fun deleteLink(lid: Int) {
       return withContext(Dispatchers.IO) {
           dao.deleteLink(lid)
       }
    }

    override suspend fun relocateLink(lid: Int, gid: Int) {
        return withContext(Dispatchers.IO) {
            dao.relocateLink(lid, gid)
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
            dao.updateLink(lid, title, url, memo, image_path)
        }
    }
}