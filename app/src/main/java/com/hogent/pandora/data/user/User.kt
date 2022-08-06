package com.hogent.pandora.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val userName: String,
    val password: String,
    val birthdate: LocalDate,
    val admin: Boolean,
    )
