package com.chanyoung.jack.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["url"]),
        Index(value = ["title"])
    ]
)

data class Link(
    @PrimaryKey(autoGenerate = true) val lid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "memo") val memo: String,
    @ColumnInfo(name = "groupId") val groupId: Int? = null,
    @ColumnInfo(name = "imagePath") val image_path : String? = null
)


@Entity
data class LinkGroup(
    @PrimaryKey(autoGenerate = true) val gid: Int = 0,
    @ColumnInfo(name = "name") val name: String
)


// Group 과 Link 간의 다대다 관계를 나타내는 매핑 테이블
@Entity(primaryKeys = ["groupId", "linkId"])
data class GroupLinkCrossRef(
    val groupId: Int,
    val linkId: Int
)
