package com.testapp.testappkotlin.data.video

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.*
import com.testapp.testappkotlin.TABLE_VIDEOS
import com.testapp.testappkotlin.data.base.BaseRepo
import com.testapp.testappkotlin.domain.model.Video
import com.testapp.testappkotlin.domain.repo.VideoRepo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class VideoRepoDatabase @Inject constructor() : BaseRepo(), VideoRepo {

    override fun getVideos(
        owner: LifecycleOwner?,
        pageNumber: Int,
        startDate: String?
    ): Observable<List<Video>> =
        PublishSubject.create<List<Video>> { emitter ->
            database.videos().getVideos().observe(owner!!, Observer<List<Video>> {
                it?.apply {
                    emitter.onNext(this)
                }
            })
        }

    override fun saveVideos(videos: List<Video>): LongArray =
        database.videos().saveVideos(*videos.toTypedArray())

    override fun saveVideo(video: Video): LongArray =
        database.videos().saveVideos(video)

    @Dao
    interface VideosDao {

        @Query("SELECT * FROM $TABLE_VIDEOS ORDER BY id")
        fun getVideos(): LiveData<List<Video>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun saveVideos(vararg videos: Video): LongArray

    }
}