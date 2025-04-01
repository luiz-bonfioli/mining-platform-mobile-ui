package br.com.mining.mobile.application

import android.app.Application
import br.com.mining.mobile.service.di.appDataModule
import br.com.mining.mobile.service.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MiningApp : Application() {

    companion object {
        lateinit var instance: MiningApp
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidContext(baseContext)
            androidLogger()
            androidFileProperties()
            modules(listOf(appDataModule, serviceModule))
        }

    }


    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}