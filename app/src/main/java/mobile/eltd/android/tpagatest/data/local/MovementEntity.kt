package mobile.eltd.android.tpagatest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import mobile.eltd.android.tpagatest.domain.model.Movement
import mobile.eltd.android.tpagatest.domain.model.MovementType

@Entity(tableName = "movements")
data class MovementEntity(
    @PrimaryKey val id: Int,
    val description: String,
    val amount: Double,
    val type: String,
    val date: Long,
)

fun MovementEntity.toDomain(): Movement =
    Movement(
        id = id,
        description = description,
        amount = amount,
        type = MovementType.valueOf(type),
        date = date,
    )

fun Movement.toEntity(): MovementEntity =
    MovementEntity(
        id = id,
        description = description,
        amount = amount,
        type = type.name,
        date = date,
    )