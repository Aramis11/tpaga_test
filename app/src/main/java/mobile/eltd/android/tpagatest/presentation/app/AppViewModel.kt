package mobile.eltd.android.tpagatest.presentation.app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mobile.eltd.android.tpagatest.domain.repository.AuthRepository

class AppViewModel(
    authRepository: AuthRepository,
) : ViewModel() {

    val authState: Flow<AuthState> = authRepository.session
        .map { session ->
            session?.let { AuthState.LoggedIn(it.username) } ?: AuthState.LoggedOut
        }
}