package mobile.eltd.android.tpagatest.di

import mobile.eltd.android.tpagatest.data.repository.AuthRepositoryImpl
import mobile.eltd.android.tpagatest.domain.repository.AuthRepository
import mobile.eltd.android.tpagatest.domain.usecase.LoginUseCase
import mobile.eltd.android.tpagatest.domain.usecase.LogoutUseCase
import mobile.eltd.android.tpagatest.presentation.app.AppViewModel
import mobile.eltd.android.tpagatest.presentation.auth.login.LoginViewModel
import mobile.eltd.android.tpagatest.presentation.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl(androidContext()) }
    single { LoginUseCase(get()) }
    single { LogoutUseCase(get()) }

    viewModel { AppViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}