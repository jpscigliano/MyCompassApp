package com.example.mycompassapp.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {
    @GET("about")
    suspend fun getAboutPage(): String
}