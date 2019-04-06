package com.testapp.testappkotlin.domain.interactor

import android.arch.lifecycle.LifecycleOwner
import android.util.Log
import com.testapp.testappkotlin.data.di.Database
import com.testapp.testappkotlin.data.di.Rest
import com.testapp.testappkotlin.domain.model.Video
import com.testapp.testappkotlin.domain.repo.VideoRepo
import com.testapp.testappkotlin.invoke
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.toSingle
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoInteractor @Inject constructor() {

    @Inject
    @field: Database
    lateinit var databaseRepo: VideoRepo

    @Inject
    @field: Rest
    lateinit var restRepo: VideoRepo

    fun listenVideos(owner: LifecycleOwner): Observable<List<Video>> = databaseRepo.getVideos(owner)

    fun loadVideos(startDate: String? = null): Observable<Int> =
        restRepo.getVideos(startDate = startDate)
            .flatMap { Observable.fromIterable(it) }
            .flatMap({ video ->
                restRepo.getVideo(video.id)
                    //to avoid {"status_code":25,"status_message":"Your request count (41) is over the allowed limit of 40."}
                    //In order for it to work better need to tweak more
                    .delay(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                    .onErrorReturn { video }
                    .map {
                        video.title({ databaseRepo.saveVideo(it).size }, { 0 })
                    }
            }, 6)


}