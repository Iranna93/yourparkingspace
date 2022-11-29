package com.reddit.test.data.source.remote

import com.reddit.test.data.source.remote.model.PostResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitService {
    @GET
    suspend fun getPosts(@Url url: String): PostResponse
}
