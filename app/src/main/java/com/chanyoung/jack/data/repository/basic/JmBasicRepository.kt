package com.chanyoung.jack.data.repository.basic

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup

interface LinkRepository {

    suspend fun insertLink(link: Link)
    suspend fun getAllLinks(): List<Link>
}

interface GroupRepository {

    suspend fun insertGroup(group: LinkGroup)
    suspend fun getAllGroup() : List<LinkGroup>
    suspend fun hasUserCreatedGroup(): Boolean
    suspend fun getGroupCount()
    suspend fun createDefaultGroup()

}