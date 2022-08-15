package com.hogent.pandora.data.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class PostComment(
    @PrimaryKey(autoGenerate = true)
    val commentId: Int,
    val postParentId: Int,
    val content: String,
    val usersFavorite: List<Int>,
    val dateAdded: LocalDate,
)
