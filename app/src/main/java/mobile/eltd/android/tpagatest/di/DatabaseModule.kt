package mobile.eltd.android.tpagatest.di

import androidx.room.Room
import mobile.eltd.android.tpagatest.data.local.TpagaDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TpagaDatabase::class.java,
            "tpaga.db",
        ).build()
    }
    single { get<TpagaDatabase>().movementDao() }
}