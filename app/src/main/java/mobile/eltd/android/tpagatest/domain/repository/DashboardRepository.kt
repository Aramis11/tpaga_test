package mobile.eltd.android.tpagatest.domain.repository

import mobile.eltd.android.tpagatest.domain.model.DashboardData

interface DashboardRepository {
    suspend fun fetchDashboard(): Result<DashboardData?>
}