package mobile.eltd.android.tpagatest.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mobile.eltd.android.tpagatest.domain.usecase.LogoutUseCase

class HomeViewModel(
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    fun onLogout() {
        viewModelScope.launch { logoutUseCase() }
    }
}