package mobile.eltd.android.tpagatest.data.remote

import retrofit2.http.GET

interface UserApi {

    @GET("users/1")
    suspend fun getUser(): UserDto
}