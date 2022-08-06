package com.hogent.pandora.data.post

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.hogent.pandora.data.user.User

@Entity(tableName = "post_table")
data class UserWithPosts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userCreatorId"
    )
    val posts: List<Post>
)
