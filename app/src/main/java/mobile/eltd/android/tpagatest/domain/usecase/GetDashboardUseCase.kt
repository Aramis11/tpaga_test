package mobile.eltd.android.tpagatest.domain.usecase

import mobile.eltd.android.tpagatest.domain.model.DashboardData
import mobile.eltd.android.tpagatest.domain.repository.DashboardRepository

class GetDashboardUseCase(
    private val dashboardRepository: DashboardRepository,
) {
    suspend operator fun invoke(): Result<DashboardData?> = dashboardRepository.fetchDashboard()
}