package com.testapp.testappkotlin.presentation.videodetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.testapp.testappkotlin.R
import com.testapp.testappkotlin.domain.model.Video
import com.testapp.testappkotlin.presentation.base.BaseActivity
import com.testapp.testappkotlin.presentation.base.BaseFragment
import com.testapp.testappkotlin.presentation.value
import com.testapp.testappkotlin.presentation.withActivity
import org.jetbrains.anko.selector

class VideoDetailsFragment : BaseFragment<VideoDetailsViewModel>() {

    companion object {
        const val VIDEO_ITEM = "video_item"
    }

    override fun getViewModelClass(): Class<VideoDetailsViewModel> = VideoDetailsViewModel::class.java

    override fun getLayoutResource(): Int = R.layout.fragment_video_details

    override fun beforeViewInit() {
        viewModel?.video = arguments.value(VIDEO_ITEM)
    }

}