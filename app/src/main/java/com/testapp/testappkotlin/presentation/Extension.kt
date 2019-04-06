package com.testapp.testappkotlin.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.widget.ImageView
import androidx.navigation.Navigation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.testapp.testappkotlin.R
import com.testapp.testappkotlin.invoke
import com.testapp.testappkotlin.presentation.di.GlideApp
import com.testapp.testappkotlin.presentation.di.GlideRequest
import org.jetbrains.anko.bundleOf
import java.io.File
import java.lang.IllegalArgumentException

inline fun Fragment.navigate(@IdRes action: Int, @IdRes fragmentId: Int, closure: Bundle.() -> Unit) {
    try {
        view?.apply {
            Navigation.findNavController(this).apply {
                if (currentDestination?.id == fragmentId)
                    navigate(action, bundleOf().apply { closure(this) })

            }
        }
    } catch (ignored: Exception) {

    }
}

fun Fragment.navigate(@IdRes action: Int, @IdRes fragmentId: Int, vararg extras: Pair<String, Any?>) = navigate(action, fragmentId) { putAll(bundleOf(*extras)) }

fun Fragment.navigate(@IdRes action: Int, @IdRes fragmentId: Int, extras: Bundle?) = navigate(action, fragmentId) { putAll(extras) }

inline fun <reified T : Activity?> Fragment.withActivity(block: T.() -> Unit) =
        activity?.let { block(activity as T) }

inline fun <reified T : Activity?, reified R : Any?> Fragment.withActivity(block: T.() -> R): R? =
        activity({ block(this as T) }, { null })

inline fun <reified T : Fragment> T.withContext(block: T.(context: Context) -> Unit) {
    activity?.apply {
        block(this)
    }
}

inline fun <reified T> Bundle?.value(key: String): T? = this?.get(key) as? T

fun Activity.viewLink(link: String = "http://www.google.com") {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)));
}

fun <T : Fragment> T.withExtras(extras: Bundle.() -> Unit): T =
        this.apply {
            bundleOf().apply {
                extras(this)
                arguments = this
            }
        }

fun ImageView.loadImage(
        imageUri: String?,
        localFile: Boolean = false,
        fullImage: Boolean = false,
        @DrawableRes placeholder: Int = R.drawable.placeholder,
        fromCache: Boolean = false,
        @DrawableRes placeholderDrawable: Drawable? = null
) {
    try {
        val glide = GlideApp.with(this)
        var glideRequest: GlideRequest<Drawable> = if (localFile)
            glide.load(File(imageUri))
        else
            glide.load(imageUri)
        glideRequest = glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(false)
        if (placeholderDrawable == null)
            glideRequest.placeholder(placeholder)
        else
            glideRequest.placeholder(placeholderDrawable)
        if (fromCache)
            glideRequest = glideRequest.onlyRetrieveFromCache(true)
        if (fullImage) {
            glideRequest
                    .encodeQuality(70)
                    .into(this)
        } else {
            glideRequest
                    .encodeQuality(70)
                    .into(this)
        }
    } catch (e: IllegalArgumentException) {
        //activity destroyed
        e.printStackTrace()
    }
}