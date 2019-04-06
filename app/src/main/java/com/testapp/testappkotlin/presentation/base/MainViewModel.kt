package com.testapp.testappkotlin.presentation.base

import android.databinding.ObservableField
import com.testapp.testappkotlin.default
import com.testapp.testappkotlin.domain.interactor.VideoInteractor
import com.testapp.testappkotlin.toDisposables
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainViewModel @Inject constructor() : BaseViewModel<BaseView>() {

    val progress: ObservableField<Boolean> = ObservableField()

}