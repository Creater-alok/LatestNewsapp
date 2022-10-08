package com.example.latestnews

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //base url sholud only contain the main site, EVERYTHING ELSE in @GET
    val api:Newsapi by lazy {
        Retrofit.Builder().
        baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Newsapi::class.java)//same name as interface
    }

}