package mobile.eltd.android.tpagatest.domain.usecase

import mobile.eltd.android.tpagatest.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}