package com.hogent.pandora.data.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "post_table")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userCreatorId: Int,
    val content: String,
    val usersFavorite: List<Int>,
    val checkedByAdmin: Boolean,
    val dateAdded: LocalDate,
)
