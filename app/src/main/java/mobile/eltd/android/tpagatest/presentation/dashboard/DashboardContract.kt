package mobile.eltd.android.tpagatest.presentation.dashboard

import androidx.annotation.StringRes
import mobile.eltd.android.tpagatest.domain.model.DashboardData

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Success(val dashboard: DashboardData) : DashboardUiState
    data class Error(@StringRes val messageRes: Int) : DashboardUiState
    data object Empty : DashboardUiState
}

sealed interface DashboardEvent {
    data object Retry : DashboardEvent
}