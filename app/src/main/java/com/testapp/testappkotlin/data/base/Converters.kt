package com.testapp.testappkotlin.data.base

import android.arch.persistence.room.TypeConverter
import com.testapp.testappkotlin.domain.model.Genre

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromGenreList(list: List<Genre>?): String? = list?.joinToString(", ") { it.name }
    
    @TypeConverter
    @JvmStatic
    fun toGenreList(list: String?): List<Genre>? = list?.split(", ")?.map { Genre(it) }

}