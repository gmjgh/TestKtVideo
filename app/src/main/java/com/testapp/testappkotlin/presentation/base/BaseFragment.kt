package  com.testapp.testappkotlin.presentation.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v7.app.AppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.*
import javax.inject.Inject
import android.view.*
import com.testapp.testappkotlin.invoke
import com.testapp.testappkotlin.presentation.withActivity


abstract class BaseFragment<T : ViewModel> : BaseView, DaggerFragment(), Bindable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override var binding: ViewDataBinding? = null

    val viewModel: T? by lazy { ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeViewInit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return bind(customTheme()({
            inflater.cloneInContext(ContextThemeWrapper(activity, this))
        }, {
            inflater
        }), container, getLayoutResource(), viewModel)
    }

    @StyleRes
    open fun customTheme(): Int? = null

    abstract fun getViewModelClass(): Class<T>

    fun setTitle(title: String) {
        activity?.title = title
    }

    fun addBackArrow(enable : Boolean) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (viewModel as? BaseViewModel<BaseView>)?.view = this
        (viewModel as? BaseViewModel<BaseView>)?.lifecycleOwner = this.viewLifecycleOwner

        onViewInit(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposePendingActions()
        (viewModel as? BaseViewModel<BaseView>)?.view = null
        (viewModel as? BaseViewModel<BaseView>)?.lifecycleOwner = null
        clearFindViewByIdCache()
    }

    protected abstract fun getLayoutResource(): Int

    override fun showError(message: String?, title: String?) = withActivity<BaseActivity> { showError(message, title) }

    override fun showMessage(message: String, title: String?): Unit? = withActivity<BaseActivity> { showMessage(message, title) }

    override fun showSnackBar( message: String?): Unit? = withActivity<BaseActivity> { showSnackBar(message) }

    override fun showProgress() {
        withActivity<BaseActivity> { showProgress() }
    }

    override fun hideProgress() {
        withActivity<BaseActivity> { hideProgress() }
    }
}