package mobile.eltd.android.tpagatest.data.remote

import retrofit2.http.GET

interface PostApi {

    @GET("posts?userId=1")
    suspend fun getPosts(): List<PostDto>
}