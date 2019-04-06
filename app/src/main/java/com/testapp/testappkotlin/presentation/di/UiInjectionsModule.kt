package com.testapp.testappkotlin.presentation.di

import com.testapp.testappkotlin.presentation.base.BaseActivity
import com.testapp.testappkotlin.presentation.videodetails.VideoDetailsFragment
import com.testapp.testappkotlin.presentation.videolist.VideosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiInjectionsModule {

    @ContributesAndroidInjector
    abstract fun injectMain(): BaseActivity

    @ContributesAndroidInjector
    abstract fun injectVideosFragment(): VideosFragment

    @ContributesAndroidInjector
    abstract fun injectVideoDetailsFragment(): VideoDetailsFragment

}