package mobile.eltd.android.tpagatest.domain.model

enum class MovementType { INCOME, EXPENSE }

data class Movement(
    val id: Int,
    val description: String,
    val amount: Double,
    val type: MovementType,
    val date: Long,
)