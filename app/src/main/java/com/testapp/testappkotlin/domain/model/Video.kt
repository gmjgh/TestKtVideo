package com.testapp.testappkotlin.domain.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.testapp.testappkotlin.TABLE_VIDEOS
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_VIDEOS)
@Parcelize
data class Video(
    val title: String?,
    val poster_path: String?,
    val adult: Boolean?,
    val original_language: String?,
    val genres: List<Genre>?,
    val overview: String?,
    val release_date: String?,
    @PrimaryKey
    val id: Int
): Parcelable
