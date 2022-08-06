package com.hogent.pandora.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromStringDate(value: String?): LocalDate? {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

}