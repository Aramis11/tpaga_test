package mobile.eltd.android.tpagatest.domain.usecase

import mobile.eltd.android.tpagatest.domain.repository.AuthRepository

sealed interface LoginResult {
    data object Success : LoginResult
    data object EmptyFields : LoginResult
}

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(username: String, password: String): LoginResult {
        if (username.isBlank() || password.isBlank()) {
            return LoginResult.EmptyFields
        }
        authRepository.login(username.trim())
        return LoginResult.Success
    }
}