package mobile.eltd.android.tpagatest.data.repository

import android.util.Log
import mobile.eltd.android.tpagatest.core.MOCK_AMOUNT
import mobile.eltd.android.tpagatest.data.local.MovementDao
import mobile.eltd.android.tpagatest.data.local.MovementEntity
import mobile.eltd.android.tpagatest.data.local.toDomain
import mobile.eltd.android.tpagatest.data.local.toEntity
import mobile.eltd.android.tpagatest.data.remote.PostApi
import mobile.eltd.android.tpagatest.data.remote.PostDto
import mobile.eltd.android.tpagatest.domain.model.Movement
import mobile.eltd.android.tpagatest.domain.model.MovementType
import mobile.eltd.android.tpagatest.domain.repository.MovementsRepository

class MovementsRepositoryImpl(
    private val postApi: PostApi,
    private val movementDao: MovementDao,
) : MovementsRepository {

    override suspend fun fetchMovements(): Result<List<Movement>> =
        try {
            val posts = postApi.getPosts()
            val movements = posts
                .filter { it.title.isNotBlank() }
                .map { it.toMovement() }
            if (movements.isNotEmpty()) {
                movementDao.clear()
                movementDao.insertAll(movements.map(Movement::toEntity))
            }
            Result.success(movements)
        } catch (e: Exception) {
            Log.e("MovementsRepo", "MovementsRepositoryImpl fetchMovements fail", e)
            Result.failure(e)
        }

    override suspend fun getCachedMovements(): List<Movement> =
        movementDao.getAll().map(MovementEntity::toDomain)

    private fun PostDto.toMovement(): Movement =
        Movement(
            id = id,
            description = title,
            amount = id * MOCK_AMOUNT,
            type = if (id % 2 == 0) MovementType.INCOME else MovementType.EXPENSE,
            date = System.currentTimeMillis(),
        )
}