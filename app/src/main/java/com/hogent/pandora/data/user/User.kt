package com.hogent.pandora.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hogent.pandora.data.post.Post
import java.time.LocalDate

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val userName: String,
    val password: String,
    val birthdate: LocalDate,
    val admin: Boolean,
    )
