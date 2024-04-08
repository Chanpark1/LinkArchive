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
}