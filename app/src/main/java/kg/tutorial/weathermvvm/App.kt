package kg.tutorial.weathermvvm

import android.app.Application
import kg.tutorial.weathermvvm.di.dataModule
import kg.tutorial.weathermvvm.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App :Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(vmModule, dataModule))
        }
    }
}