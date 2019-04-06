package  com.testapp.testappkotlin.presentation.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.ActivityInfo
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import androidx.navigation.Navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import androidx.navigation.Navigation
import com.testapp.testappkotlin.R
import com.testapp.testappkotlin.invoke
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity : DaggerAppCompatActivity(), BaseView, Bindable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mainViewModel: MainViewModel

    override var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (resources.getBoolean(R.bool.is_tablet)){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.view = this
        mainViewModel.lifecycleOwner = this
        beforeViewInit()
        bind(getLayoutResource(), mainViewModel)
        onViewInit(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(this, R.id.content).navigateUp()

    protected fun getLayoutResource(): Int = R.layout.activity_base

    override fun showError(message: String?, title: String?) = showSnackBar(message)

    override fun showMessage(message: String, title: String?): Unit? = showSnackBar(message)

    override fun showSnackBar(message: String?): Unit? =
        binding?.root?.run { if (message != null) Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show() }

    override fun showProgress() {
        mainViewModel.progress.set(true)
    }

    override fun hideProgress() {
        mainViewModel.progress.set(false)
    }

    override fun navigateBack() {
        onSupportNavigateUp()
    }

}
