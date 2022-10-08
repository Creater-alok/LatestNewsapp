package com.example.latestnews

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Newsapi {
    @GET("v2/top-headlines")
    suspend fun get(@Query("country") code:String,@Query("category") cat:String,@Query("pageSize") pd:Int,@Query("apikey") key:String): Response<News>
}