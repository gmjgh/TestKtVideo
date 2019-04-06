package com.testapp.testappkotlin

import com.google.gson.Gson
import com.testapp.testappkotlin.presentation.base.BaseView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

fun <T> Observable<T>.applySchedulers(): Observable<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.propagateErrors(function: (error: Throwable) -> Unit): Observable<T> =
        doOnError {
            function(it)
        }.onExceptionResumeNext(Observable.empty<T>())
                .onErrorResumeNext(Observable.empty<T>())
                .onExceptionResumeNext(Observable.empty<T>())

fun <T> Observable<T>.default(function: (e: Throwable) -> Unit): Observable<T> =
        applySchedulers().propagateErrors(function).retry()

fun <T> Observable<T>.defaultActions(view: BaseView?, retryCount: Int = 3): Observable<T> =
        doOnSubscribe { view?.showProgress() }
                .doFinally { view?.hideProgress() }
                .doOnTerminate { view?.hideProgress() }
                .retry { count, _ -> count <= retryCount }

fun Disposable.toDisposables(disposables: CompositeDisposable) {
    disposables.add(this)
}

fun Long.formatTimestamp(format: String): String = SimpleDateFormat(format, Locale.getDefault()).format(this)

val Int.daysToMilliseconds get(): Long = this.toLong() * 24L * 60L * 60L * 1000L

fun Any.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.toObject(): T =
        Gson().fromJson(this, T::class.java)

fun <T> String.toObject(type: Type): T = Gson().fromJson(this, type)

inline operator fun Boolean.invoke(functionTrue: () -> Unit) {
    if (this) functionTrue()
}

inline operator fun <reified T, reified R> T?.invoke(functionTrue: T.() -> R, functionFalse: () -> R): R =
        if (this != null)
            if (!(this is Boolean))
                functionTrue()
            else if (this)
                functionTrue()
            else
                functionFalse()
        else
            functionFalse()