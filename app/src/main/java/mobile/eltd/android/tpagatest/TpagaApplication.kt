package mobile.eltd.android.tpagatest

import android.app.Application
import mobile.eltd.android.tpagatest.di.appModule
import mobile.eltd.android.tpagatest.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TpagaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TpagaApplication)
            modules(appModule, networkModule)
        }
    }
}