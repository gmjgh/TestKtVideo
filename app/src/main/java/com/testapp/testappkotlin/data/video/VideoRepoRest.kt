package com.testapp.testappkotlin.data.video

import android.arch.lifecycle.LifecycleOwner
import com.testapp.testappkotlin.data.*
import com.testapp.testappkotlin.data.base.BaseRepo
import com.testapp.testappkotlin.domain.model.Video
import com.testapp.testappkotlin.domain.model.VideoPage
import com.testapp.testappkotlin.domain.repo.VideoRepo
import com.testapp.testappkotlin.formatTimestamp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class VideoRepoRest @Inject constructor() : BaseRepo(), VideoRepo {

    override fun getVideos(
            owner: LifecycleOwner?,
            pageNumber: Int,
            startDate: String?
    ): Observable<List<Video>> =
        retrofit.service<VideoService>()
            .getVideos(pageNumber, startDate)
            .map { it.results }
            .flatMap { Observable.fromIterable(it) }
            .filter { it.adult == false }
            .sorted { o1, o2 -> o1.id - o2.id }
            .toList()
            .toObservable()

    override fun getVideo(videoId: Int): Observable<Video> =
        retrofit.service<VideoService>()
            .getVideo(videoId)

    interface VideoService {

        @GET(VIDEOS)
        fun getVideos(
            @Query("page") pageNumber: Int = 1,
            @Query("start_date") startDate: String? = null,
            @Query("end_date") endDate: String? = System.currentTimeMillis().formatTimestamp("dd.MM.yyyy")
        ): Observable<VideoPage>

        @GET(VIDEO_DETAILS)
        fun getVideo(@Path(VIDEO_ID) movieId: Int): Observable<Video>

    }
}