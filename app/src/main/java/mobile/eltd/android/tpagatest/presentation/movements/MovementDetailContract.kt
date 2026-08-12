package mobile.eltd.android.tpagatest.presentation.movements

import mobile.eltd.android.tpagatest.domain.model.Movement

sealed interface MovementDetailUiState {
    data object Loading : MovementDetailUiState
    data class Success(val movement: Movement) : MovementDetailUiState
    data class Error(val messageRes: Int) : MovementDetailUiState
}