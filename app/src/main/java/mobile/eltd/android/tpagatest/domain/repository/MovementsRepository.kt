package mobile.eltd.android.tpagatest.domain.repository

import mobile.eltd.android.tpagatest.domain.model.Movement

interface MovementsRepository {

    suspend fun fetchMovements(): Result<List<Movement>>

    suspend fun getCachedMovements(): List<Movement>
}