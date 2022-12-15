package com.example.checkacronyms.network

import com.example.checkacronyms.data.AcronymsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("dictionary.py")
    suspend fun getDefinitions(@Query("sf") shortForm: String): Response<List<AcronymsResponse>>
}