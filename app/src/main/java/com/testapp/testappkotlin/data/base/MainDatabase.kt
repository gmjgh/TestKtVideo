package com.testapp.testappkotlin.data.base

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.testapp.testappkotlin.data.video.VideoRepoDatabase
import com.testapp.testappkotlin.domain.model.Genre
import com.testapp.testappkotlin.domain.model.Video

@Database(entities = [Video::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MainDatabase : RoomDatabase(){

    abstract fun videos(): VideoRepoDatabase.VideosDao

}

