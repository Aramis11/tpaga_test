package mobile.eltd.android.tpagatest.presentation.movements

import mobile.eltd.android.tpagatest.R
import mobile.eltd.android.tpagatest.domain.model.Movement
import mobile.eltd.android.tpagatest.domain.model.MovementType

enum class FilterType {
    ALL, INCOME, EXPENSE;

    fun labelRes(): Int = when (this) {
        ALL -> R.string.filter_all
        INCOME -> R.string.filter_income
        EXPENSE -> R.string.filter_expense
    }
}

fun List<Movement>.filterBy(type: FilterType): List<Movement> = when (type) {
    FilterType.ALL -> this
    FilterType.INCOME -> filter { it.type == MovementType.INCOME }
    FilterType.EXPENSE -> filter { it.type == MovementType.EXPENSE }
}

sealed interface MovementsUiState {
    data object Loading : MovementsUiState
    data class Success(
        val movements: List<Movement>,
        val filter: FilterType,
        val offline: Boolean,
    ) : MovementsUiState
    data class Error(val messageRes: Int) : MovementsUiState
    data object Empty : MovementsUiState
}

sealed interface MovementsEvent {
    data class SelectFilter(val filter: FilterType) : MovementsEvent
    data object Retry : MovementsEvent
}