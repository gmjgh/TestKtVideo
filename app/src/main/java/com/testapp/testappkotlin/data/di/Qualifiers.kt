@file:Suppress("DEPRECATED_JAVA_ANNOTATION")

package com.testapp.testappkotlin.data.di

import java.lang.annotation.Documented
import javax.inject.Qualifier

@Qualifier
@Documented
@Retention(AnnotationRetention.RUNTIME)
annotation class Database()

@Qualifier
@Documented
@Retention(AnnotationRetention.RUNTIME)
annotation class Rest()
