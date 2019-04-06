package com.testapp.testappkotlin.presentation.util

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.testapp.testappkotlin.presentation.di.GlideApp
import com.testapp.testappkotlin.presentation.loadImage
import android.support.v7.widget.SimpleItemAnimator



@BindingAdapter("url")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.loadImage(url)
}

@BindingAdapter("disableAnimations")
fun disableRecyclerViewAnimations(list: RecyclerView, disable: Boolean){
    (list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = !disable
}