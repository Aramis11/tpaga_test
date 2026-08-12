package mobile.eltd.android.tpagatest.di

import mobile.eltd.android.tpagatest.data.local.FilterPreferences
import mobile.eltd.android.tpagatest.data.repository.AuthRepositoryImpl
import mobile.eltd.android.tpagatest.domain.repository.AuthRepository
import mobile.eltd.android.tpagatest.domain.usecase.GetCachedMovementsUseCase
import mobile.eltd.android.tpagatest.domain.usecase.GetDashboardUseCase
import mobile.eltd.android.tpagatest.domain.usecase.GetMovementsUseCase
import mobile.eltd.android.tpagatest.domain.usecase.LoginUseCase
import mobile.eltd.android.tpagatest.domain.usecase.LogoutUseCase
import mobile.eltd.android.tpagatest.presentation.app.AppViewModel
import mobile.eltd.android.tpagatest.presentation.auth.login.LoginViewModel
import mobile.eltd.android.tpagatest.presentation.dashboard.DashboardViewModel
import mobile.eltd.android.tpagatest.presentation.home.HomeViewModel
import mobile.eltd.android.tpagatest.presentation.movements.MovementDetailViewModel
import mobile.eltd.android.tpagatest.presentation.movements.MovementsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl(androidContext()) }
    single { FilterPreferences(androidContext()) }
    single { LoginUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { GetDashboardUseCase(get()) }
    single { GetMovementsUseCase(get()) }
    single { GetCachedMovementsUseCase(get()) }

    viewModel { AppViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { MovementsViewModel(get(), get(), get(), get()) }
    viewModel { MovementDetailViewModel(get()) }
}