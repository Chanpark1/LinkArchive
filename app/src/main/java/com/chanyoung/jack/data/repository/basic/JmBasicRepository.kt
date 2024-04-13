package com.chanyoung.jack.data.repository.basic

import androidx.paging.PagingData
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import java.util.concurrent.Flow

interface LinkRepository {

    suspend fun insertLink(link: Link)
    suspend fun getAllLinks(): List<Link>
    suspend fun getPaginatedLinks(index : Int, loadSize : Int) : List<Link>

    suspend fun getLink(lid : Int) : Link
}

interface GroupRepository {

    suspend fun insertGroup(group: LinkGroup)
    suspend fun getAllGroup() : List<LinkGroup>
    suspend fun hasUserCreatedGroup(): Boolean
    suspend fun getGroupCount()
    suspend fun createDefaultGroup()
    suspend fun checkDuplicateGroup(groupName : String) : Int
    suspend fun getGroupId(groupName : String) : Int


}