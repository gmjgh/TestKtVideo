package com.testapp.testappkotlin.presentation.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.view.View
import io.reactivex.annotations.Nullable

interface BaseView{

    fun showError(message: String?, title: String? = null): Unit?

    fun showMessage(message: String, title: String? = null): Unit?

    fun showSnackBar(message: String?): Unit?

    fun showProgress()

    fun hideProgress()

    fun onViewInit(@Nullable savedInstanceState: Bundle? = null){}

    fun beforeViewInit(){}

    fun disposePendingActions(){}

    fun navigateBack(){}

    fun navigateForward(@IdRes actionRes: Int, @Nullable extra: Bundle? = null){}

}