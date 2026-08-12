package mobile.eltd.android.tpagatest.di

import kotlinx.serialization.json.Json
import mobile.eltd.android.tpagatest.core.network.NetworkMonitor
import mobile.eltd.android.tpagatest.data.remote.PostApi
import mobile.eltd.android.tpagatest.data.remote.UserApi
import mobile.eltd.android.tpagatest.data.repository.DashboardRepositoryImpl
import mobile.eltd.android.tpagatest.data.repository.MovementsRepositoryImpl
import mobile.eltd.android.tpagatest.domain.repository.DashboardRepository
import mobile.eltd.android.tpagatest.domain.repository.MovementsRepository
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

val networkModule = module {
    single { Json { ignoreUnknownKeys = true } }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single { get<Retrofit>().create(UserApi::class.java) }
    single { get<Retrofit>().create(PostApi::class.java) }

    single<DashboardRepository> { DashboardRepositoryImpl(get()) }
    single<MovementsRepository> { MovementsRepositoryImpl(get(), get()) }
    single { NetworkMonitor(get()) }
}