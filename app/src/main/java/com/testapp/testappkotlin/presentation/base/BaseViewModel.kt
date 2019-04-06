package com.testapp.testappkotlin.presentation.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import com.testapp.testappkotlin.presentation.custom.onUi
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job

open class BaseViewModel<V : BaseView> : ViewModel() {

    var view: V? = null

    var lifecycleOwner: LifecycleOwner? = null

    protected var disposables = CompositeDisposable()

    protected val job: Job = Job()

    open fun dispose() {
        disposables.clear()
        job.cancel()
        view = null
        lifecycleOwner = null
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    fun handleCommonErrors(e: Throwable?) {
        e?.printStackTrace()
        onUi(job) {
            e?.apply {
                localizedMessage?.apply {
                    view?.showError(this)
                }
            }
        }
    }
}