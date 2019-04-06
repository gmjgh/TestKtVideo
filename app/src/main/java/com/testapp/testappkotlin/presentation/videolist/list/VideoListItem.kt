package com.testapp.testappkotlin.presentation.videolist.list

import android.view.View
import com.testapp.testappkotlin.R
import com.testapp.testappkotlin.domain.model.Video
import com.testapp.testappkotlin.presentation.base.BaseListItem

class VideoListItem constructor(val video: Video, val closure: (Video) -> Unit) : BaseListItem {

    override val layoutRes: Int = R.layout.item_video
    override var comparableId: String? = video.hashCode().toString()

    fun onClick(view: View) {
        closure.invoke(video)
    }
}