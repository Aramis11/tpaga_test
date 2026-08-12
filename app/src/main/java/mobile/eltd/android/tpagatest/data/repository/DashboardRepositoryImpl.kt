package mobile.eltd.android.tpagatest.data.repository

import android.util.Log
import mobile.eltd.android.tpagatest.core.MOCK_AMOUNT
import mobile.eltd.android.tpagatest.data.remote.UserApi
import mobile.eltd.android.tpagatest.domain.model.DashboardData
import mobile.eltd.android.tpagatest.domain.repository.DashboardRepository

class DashboardRepositoryImpl(
    private val userApi: UserApi,
) : DashboardRepository {

    override suspend fun fetchDashboard(): Result<DashboardData?> =
        try {
            val user = userApi.getUser()
            val dashboard = user
                .takeIf { it.name.isNotBlank() }
                ?.let { DashboardData(name = it.name, balance = it.id * MOCK_AMOUNT) }
            Result.success(dashboard)
        } catch (e: Exception) {
            Log.e("DashboardRepo", "DashboardRepositoryImpl fail", e)
            Result.failure(e)
        }
}