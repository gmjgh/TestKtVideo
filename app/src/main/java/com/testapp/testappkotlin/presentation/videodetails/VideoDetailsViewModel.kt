package com.testapp.testappkotlin.presentation.videodetails

import com.testapp.testappkotlin.*
import com.testapp.testappkotlin.domain.interactor.VideoInteractor
import com.testapp.testappkotlin.domain.model.Video
import com.testapp.testappkotlin.presentation.base.BaseListItem
import com.testapp.testappkotlin.presentation.base.BaseView
import com.testapp.testappkotlin.presentation.base.BaseViewModel
import com.testapp.testappkotlin.presentation.base.GenericDiff
import com.testapp.testappkotlin.presentation.videolist.list.VideoListItem
import me.tatarka.bindingcollectionadapter2.OnItemBind
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import javax.inject.Inject

class VideoDetailsViewModel @Inject constructor(val interactor: VideoInteractor) : BaseViewModel<BaseView>() {

    var items: DiffObservableList<BaseListItem> = DiffObservableList(GenericDiff)

    val onItemBind: OnItemBind<BaseListItem> =
            OnItemBind { itemBinding, position, item -> itemBinding.set(BR.item, item.layoutRes) }

    var video: Video? = null

    val text: String? by lazy { video?.toJson() }

}