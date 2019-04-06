package com.testapp.testappkotlin.data.di

import com.testapp.testappkotlin.data.video.VideoRepoDatabase
import com.testapp.testappkotlin.data.video.VideoRepoRest
import com.testapp.testappkotlin.domain.repo.VideoRepo
import dagger.Binds
import dagger.Module

@Module
abstract class RepoBindModule {

    @Binds @Database
    abstract fun provideAuthRepoGoogle(restRepo: VideoRepoDatabase): VideoRepo

    @Binds @Rest
    abstract fun provideAuthRepoFacebook(restRepo: VideoRepoRest): VideoRepo

}