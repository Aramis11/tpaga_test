package mobile.eltd.android.tpagatest.presentation.movements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobile.eltd.android.tpagatest.R
import mobile.eltd.android.tpagatest.domain.usecase.GetCachedMovementsUseCase

class MovementDetailViewModel(
    private val getCachedMovementsUseCase: GetCachedMovementsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovementDetailUiState>(MovementDetailUiState.Loading)
    val uiState: StateFlow<MovementDetailUiState> = _uiState.asStateFlow()

    fun load(movementId: Int) {
        _uiState.value = MovementDetailUiState.Loading
        viewModelScope.launch {
            val movement = getCachedMovementsUseCase().firstOrNull { it.id == movementId }
            _uiState.value = if (movement != null) {
                MovementDetailUiState.Success(movement)
            } else {
                MovementDetailUiState.Error(R.string.movement_detail_not_found)
            }
        }
    }
}