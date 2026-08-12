package mobile.eltd.android.tpagatest.data.repository

import android.util.Log
import mobile.eltd.android.tpagatest.data.remote.UserApi
import mobile.eltd.android.tpagatest.domain.model.DashboardData
import mobile.eltd.android.tpagatest.domain.repository.DashboardRepository

class DashboardRepositoryImpl(
    private val userApi: UserApi,
) : DashboardRepository {

    private companion object {
        const val BALANCE_FACTOR = 1000.0
    }

    override suspend fun fetchDashboard(): Result<DashboardData?> =
        try {
            val user = userApi.getUser()
            val dashboard = user
                .takeIf { it.name.isNotBlank() }
                ?.let { DashboardData(name = it.name, balance = it.id * BALANCE_FACTOR) }
            Result.success(dashboard)
        } catch (e: Exception) {
            Log.e("DashboardRepo", "DashboardRepositoryImpl failed", e)
            Result.failure(e)
        }
}