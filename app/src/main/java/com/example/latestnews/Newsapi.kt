package com.example.latestnews

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Newsapi {
    @GET("v2/everything")
    //suspend fun get(@Query("country") code:String,@Query("pageSize") pd:Int,@Query("apikey") key:String): Response<News>
    suspend fun get(@Query("sources") s:String,@Query("pageSize") pd:Int,@Query("apikey") key:String): Response<News>
}