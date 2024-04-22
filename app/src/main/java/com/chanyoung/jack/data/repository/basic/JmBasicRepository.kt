package com.chanyoung.jack.data.repository.basic

import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup

interface LinkRepository {

    suspend fun insertLink(link: Link)
    suspend fun getAllLinks(): List<Link>
    suspend fun getPaginatedLinks(index : Int, loadSize : Int) : List<Link>
    suspend fun getPaginatedLinksByGroupId(index : Int, loadSize : Int, gid : Int) : List<Link>
    suspend fun getLink(lid : Int) : Link
    suspend fun getLinksInGroup(gid : Int): List<Link>
    suspend fun deleteLink(link : Link)
    suspend fun relocateLink(lid : Int, oldGid : Int, newGid : Int)
    suspend fun updateLink(lid : Int, title : String, url : String, memo : String, image_path : String?)
    suspend fun searchLinkByGroupId(gid : Int, query : String, index : Int, loadSize: Int) : List<Link>
    suspend fun searchLink(query: String, index: Int, loadSize: Int) : List<Link>
}

interface GroupRepository {

    suspend fun insertGroup(group: LinkGroup)
    suspend fun getLinkGroup(gid : Int) : LinkGroup
    suspend fun updateGroup(name : String, gid : Int)
    suspend fun getAllGroup() : List<LinkGroup>
    suspend fun getAllGroupExcept(gid : Int) : List<LinkGroup>
    suspend fun hasUserCreatedGroup(): Boolean
    suspend fun getGroupCount()
    suspend fun createDefaultGroup()
    suspend fun checkDuplicateGroup(groupName : String) : Int
    suspend fun getGroupId(groupName : String) : Int
    suspend fun getPaginatedGroups(index : Int, loadSize: Int) : List<LinkGroup>
    suspend fun deleteGroup(group : LinkGroup)


}