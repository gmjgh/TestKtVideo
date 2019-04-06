package com.testapp.testappkotlin.data.base

import retrofit2.Retrofit
import javax.inject.Inject

open class BaseRepo {

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var database: MainDatabase

}