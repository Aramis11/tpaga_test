package mobile.eltd.android.tpagatest.presentation.movements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobile.eltd.android.tpagatest.R
import mobile.eltd.android.tpagatest.core.network.NetworkMonitor
import mobile.eltd.android.tpagatest.data.local.FilterPreferences
import mobile.eltd.android.tpagatest.domain.model.Movement
import mobile.eltd.android.tpagatest.domain.usecase.GetCachedMovementsUseCase
import mobile.eltd.android.tpagatest.domain.usecase.GetMovementsUseCase
import java.io.IOException

class MovementsViewModel(
    private val getMovementsUseCase: GetMovementsUseCase,
    private val getCachedMovementsUseCase: GetCachedMovementsUseCase,
    private val networkMonitor: NetworkMonitor,
    private val filterPreferences: FilterPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovementsUiState>(MovementsUiState.Loading)
    val uiState: StateFlow<MovementsUiState> = _uiState.asStateFlow()

    private var currentFilter: FilterType = FilterType.ALL
    private var allMovements: List<Movement> = emptyList()

    init {
        viewModelScope.launch {
            filterPreferences.filter.collect { filter ->
                currentFilter = filter
                if (allMovements.isEmpty()) load() else emitSuccess(filter)
            }
        }
        viewModelScope.launch {
            networkMonitor.isOnline.collect { online ->
                if (online && allMovements.isNotEmpty() && isShowingOffline()) {
                    refreshFromServer()
                }
            }
        }
    }

    fun onEvent(event: MovementsEvent) {
        when (event) {
            is MovementsEvent.SelectFilter -> {
                currentFilter = event.filter
                viewModelScope.launch { filterPreferences.save(event.filter) }
                if (allMovements.isNotEmpty()) emitSuccess(event.filter)
            }
            MovementsEvent.Retry -> load()
        }
    }

    private fun isShowingOffline(): Boolean =
        (_uiState.value as? MovementsUiState.Success)?.offline == true

    private fun load() {
        _uiState.value = MovementsUiState.Loading
        viewModelScope.launch {
            val result = getMovementsUseCase()
            if (result.isSuccess) {
                val movements = result.getOrThrow()
                allMovements = movements
                if (movements.isEmpty()) {
                    _uiState.value = MovementsUiState.Empty
                } else {
                    emitSuccess(currentFilter)
                }
            } else {
                val error = result.exceptionOrNull()
                val cached = getCachedMovementsUseCase()
                if (cached.isNotEmpty()) {
                    allMovements = cached
                    emitSuccess(currentFilter, offline = true)
                } else {
                    _uiState.value = MovementsUiState.Error(
                        if (error is IOException) {
                            R.string.dashboard_error_network
                        } else {
                            R.string.dashboard_error_generic
                        }
                    )
                }
            }
        }
    }

    private suspend fun refreshFromServer() {
        if (_uiState.value is MovementsUiState.Loading) return
        getMovementsUseCase().onSuccess { movements ->
            allMovements = movements
            emitSuccess(currentFilter)
        }
    }

    private fun emitSuccess(filter: FilterType, offline: Boolean = false) {
        _uiState.value = MovementsUiState.Success(allMovements.filterBy(filter), filter, offline)
    }
}