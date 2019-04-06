package com.testapp.testappkotlin.domain.repo

import android.arch.lifecycle.LifecycleOwner
import com.testapp.testappkotlin.domain.model.Video
import io.reactivex.Observable
import retrofit2.http.Query

interface VideoRepo {

    fun getVideos(owner: LifecycleOwner? = null,
                  pageNumber: Int = 1,
                  startDate: String? = null): Observable<List<Video>>

    fun getVideo(videoId: Int): Observable<Video> = Observable.empty()

    fun saveVideos(videos: List<Video>): LongArray = longArrayOf()

    fun saveVideo(video: Video): LongArray = longArrayOf()

}