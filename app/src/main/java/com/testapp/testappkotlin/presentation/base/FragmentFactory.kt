package com.testapp.testappkotlin.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.testapp.testappkotlin.presentation.withExtras
import org.jetbrains.anko.bundleOf

object FragmentFactory {

    inline fun <reified T : Fragment> newInstance(): T = T::class.java.newInstance()

    inline fun <reified T : Fragment> newInstance(bundle: Bundle): T = newInstance<T>().withExtras { putAll(bundle) }

    inline fun <reified T : Fragment> newInstance(vararg extras: Pair<String, Any>): T = newInstance<T>().withExtras { putAll(bundleOf(*extras)) }
}