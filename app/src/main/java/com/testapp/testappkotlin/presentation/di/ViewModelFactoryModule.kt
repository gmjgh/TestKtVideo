package com.testapp.testappkotlin.presentation.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.testapp.testappkotlin.presentation.videolist.VideosViewModel
import com.testapp.testappkotlin.presentation.base.MainViewModel
import com.testapp.testappkotlin.presentation.videodetails.VideoDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(myViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideosViewModel::class)
    internal abstract fun bindVideosViewModel(myViewModel: VideosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoDetailsViewModel::class)
    internal abstract fun bindVideoDetailsViewModel(myViewModel: VideoDetailsViewModel): ViewModel

}