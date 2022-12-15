package com.example.checkacronyms.db

import com.example.checkacronyms.network.ApiService

class ApiHelper(val apiService: ApiService) {
    suspend fun getDefinitions(shortForm: String) = apiService.getDefinitions(shortForm)
}