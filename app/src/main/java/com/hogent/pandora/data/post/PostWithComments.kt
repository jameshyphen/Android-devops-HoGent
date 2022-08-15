package com.hogent.pandora.data.post

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.hogent.pandora.data.user.User

data class PostWithComments(
    @Embedded val user: Post,
    @Relation(
        parentColumn = "postId",
        entityColumn = "postParentId"
    )
    val comments: List<PostComment>
)
