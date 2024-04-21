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
    @ColumnInfo(name = "gid") val groupId: Int = 0,
    @ColumnInfo(name = "imagePath") val image_path : String? = null
)


@Entity(
    indices = [
        Index(value = ["name"], unique = true )]
)
data class LinkGroup(
    @PrimaryKey(autoGenerate = true) val gid: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "linkCount") val linkCount : Int = 0
)


@Entity(primaryKeys = ["gid", "lid"])
data class GroupLinkCrossRef(
    val gid: Int,
    val lid: Int
)
