package mobile.eltd.android.tpagatest.domain.usecase

import mobile.eltd.android.tpagatest.domain.model.Movement
import mobile.eltd.android.tpagatest.domain.repository.MovementsRepository

class GetCachedMovementsUseCase(
    private val movementsRepository: MovementsRepository,
) {
    suspend operator fun invoke(): List<Movement> = movementsRepository.getCachedMovements()
}