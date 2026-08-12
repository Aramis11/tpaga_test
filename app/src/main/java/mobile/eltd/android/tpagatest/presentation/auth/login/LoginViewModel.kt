package mobile.eltd.android.tpagatest.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mobile.eltd.android.tpagatest.domain.usecase.LoginResult
import mobile.eltd.android.tpagatest.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnSubmit -> onSubmit(event.username, event.password)
            LoginEvent.OnErrorDismissed -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun onSubmit(username: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase(username, password)
            _uiState.update {
                it.copy(error = if (result is LoginResult.Success) null else LoginError.EMPTY_FIELDS)
            }
        }
    }
}