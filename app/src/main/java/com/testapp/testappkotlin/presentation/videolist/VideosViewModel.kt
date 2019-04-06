package com.testapp.testappkotlin.presentation.videolist

import com.testapp.testappkotlin.*
import com.testapp.testappkotlin.domain.interactor.VideoInteractor
import com.testapp.testappkotlin.presentation.base.BaseListItem
import com.testapp.testappkotlin.presentation.base.BaseView
import com.testapp.testappkotlin.presentation.base.BaseViewModel
import com.testapp.testappkotlin.presentation.base.GenericDiff
import com.testapp.testappkotlin.presentation.videodetails.VideoDetailsFragment
import com.testapp.testappkotlin.presentation.videolist.list.VideoListItem
import me.tatarka.bindingcollectionadapter2.OnItemBind
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList
import org.jetbrains.anko.bundleOf
import javax.inject.Inject

class VideosViewModel @Inject constructor(val interactor: VideoInteractor) : BaseViewModel<BaseView>() {

    var items: DiffObservableList<BaseListItem> = DiffObservableList(GenericDiff)

    val onItemBind: OnItemBind<BaseListItem> =
        OnItemBind { itemBinding, position, item -> itemBinding.set(BR.item, item.layoutRes) }

    init {
        loadData()
    }

    fun listenData() {
        lifecycleOwner?.apply {
            interactor.listenVideos(this)
                .subscribe({
                    items.update(it.map {
                        VideoListItem(it) {
                            view?.navigateForward(
                                R.id.action_fragment_videos_to_details,
                                bundleOf(VideoDetailsFragment.VIDEO_ITEM to it)
                            )
                        }
                    })
                }, { handleCommonErrors(it) })
                .toDisposables(disposables)
        }
    }

    fun loadData(days: Int? = null) {
        disposables.clear()
        interactor.loadVideos(
            days(
                { (System.currentTimeMillis() - days!!.daysToMilliseconds).formatTimestamp("dd.MM.yyyy") },
                { null })
        )
            .default { handleCommonErrors(it) }
            .subscribe({}, { handleCommonErrors(it) })
            .toDisposables(disposables)
    }

}