package mobile.eltd.android.tpagatest.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Int,
    val title: String,
)