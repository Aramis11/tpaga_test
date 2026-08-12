package mobile.eltd.android.tpagatest.presentation.app

sealed interface AuthState {
    data object Loading : AuthState
    data object LoggedOut : AuthState
    data class LoggedIn(val username: String) : AuthState
}