package  com.testapp.testappkotlin.presentation.di

import android.app.Application
import com.testapp.testappkotlin.App
import com.testapp.testappkotlin.data.di.BaseModule
import com.testapp.testappkotlin.data.di.RepoBindModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    BaseModule::class,
    UiInjectionsModule::class,
    RepoBindModule::class,
    ViewModelFactoryModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
