package com.hogent.pandora.data.post

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.hogent.pandora.data.user.User

data class UserWithPosts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val posts: List<Post>
)
