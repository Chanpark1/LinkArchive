package com.chanyoung.jack.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class LinksWithLinkGroup(
    @Embedded val link : Link,
    @Relation(
        parentColumn = "lid",
        entityColumn = "gid",
        associateBy = Junction(GroupLinkCrossRef::class)
    ) val linkGroups : List<LinkGroup>

)