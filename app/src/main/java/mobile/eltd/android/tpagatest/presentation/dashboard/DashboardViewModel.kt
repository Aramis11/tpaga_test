package mobile.eltd.android.tpagatest.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobile.eltd.android.tpagatest.R
import mobile.eltd.android.tpagatest.domain.usecase.GetDashboardUseCase
import java.io.IOException

class DashboardViewModel(
    private val getDashboardUseCase: GetDashboardUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.Retry -> load()
        }
    }

    private fun load() {
        _uiState.value = DashboardUiState.Loading
        viewModelScope.launch {
            val result = getDashboardUseCase()
            _uiState.value = result.fold(
                onSuccess = { dashboard ->
                    dashboard?.let { DashboardUiState.Success(it) } ?: DashboardUiState.Empty
                },
                onFailure = { error ->
                    DashboardUiState.Error(
                        if (error is IOException) {
                            R.string.dashboard_error_network
                        } else {
                            R.string.dashboard_error_generic
                        }
                    )
                },
            )
        }
    }
}