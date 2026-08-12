package mobile.eltd.android.tpagatest.presentation.auth.login

data class LoginUiState(
    val error: LoginError? = null,
)

enum class LoginError {
    EMPTY_FIELDS,
}

sealed interface LoginEvent {
    data class OnSubmit(val username: String, val password: String) : LoginEvent
    data object OnErrorDismissed : LoginEvent
}