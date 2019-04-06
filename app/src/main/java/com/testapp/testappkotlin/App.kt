package com.testapp.testappkotlin

import android.arch.persistence.room.Room
import android.content.Context
import com.testapp.testappkotlin.data.base.MainDatabase
import com.testapp.testappkotlin.presentation.di.AppComponent
import com.testapp.testappkotlin.presentation.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    companion object {
        @JvmStatic
        private lateinit var instance: App

        @JvmStatic
        fun getContext(): Context = instance

        @JvmStatic
        fun get(): App = getContext() as App

        val database by lazy { Room.databaseBuilder(getContext(), MainDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build() }

    }

    lateinit var appComponent: AppComponent

    init {
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build().apply {
            appComponent = this
            appComponent.inject(this@App)
        }
}