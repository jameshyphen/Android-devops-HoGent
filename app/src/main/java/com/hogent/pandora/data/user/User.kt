package com.hogent.pandora.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userName: String,
    val age: Int,
    val email: String,
    val role: Role
    )
