package mobile.eltd.android.tpagatest.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
)