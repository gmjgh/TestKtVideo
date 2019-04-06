package com.testapp.testappkotlin.presentation.videolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.testapp.testappkotlin.R
import com.testapp.testappkotlin.presentation.base.BaseActivity
import com.testapp.testappkotlin.presentation.base.BaseFragment
import com.testapp.testappkotlin.presentation.navigate
import com.testapp.testappkotlin.presentation.videodetails.VideoDetailsFragment
import com.testapp.testappkotlin.presentation.withActivity
import org.jetbrains.anko.selector

class VideosFragment : BaseFragment<VideosViewModel>() {

    override fun getViewModelClass(): Class<VideosViewModel> = VideosViewModel::class.java

    override fun getLayoutResource(): Int = R.layout.fragment_videos

    override fun onViewInit(savedInstanceState: Bundle?) {
        viewModel?.listenData()
    }

    override fun beforeViewInit() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_range) {
            showDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateForward(actionRes: Int, extra: Bundle?) {
        navigate(actionRes,R.id.fragment_videos, extra)
    }

    private fun showDialog() {
        val days = (1..14).toList()
        withActivity<BaseActivity> {
            selector("For how many days you want to see changes", days.map { it.toString() }) { _, i ->
                viewModel?.loadData(days[i])
            }
        }
    }
}