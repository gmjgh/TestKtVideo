package com.testapp.testappkotlin.presentation.di

import android.arch.lifecycle.ViewModel

import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)