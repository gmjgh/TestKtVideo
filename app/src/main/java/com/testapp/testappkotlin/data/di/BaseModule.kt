package com.testapp.testappkotlin.data.di

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.testapp.testappkotlin.*
import com.testapp.testappkotlin.data.base.MainDatabase
import com.testapp.testappkotlin.data.withApiKey
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class BaseModule {

    @Provides
    @Singleton
    fun provideApp(): App = App.get()

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(1, TimeUnit.MINUTES)
        .withApiKey()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                BuildConfig.DEBUG({ HttpLoggingInterceptor.Level.BODY },
                    { HttpLoggingInterceptor.Level.NONE })
            )
        )
        .build()

    @Provides
    @Singleton
    internal fun provideApiRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_ENDPOINT)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    internal fun provideDatabase(context: Context): MainDatabase  =
        Room.databaseBuilder(context, MainDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

}