package com.example.checkacronyms.db

class MainRepository(val apiHelper: ApiHelper) {
    suspend fun getDefinitions(shortForm: String) = apiHelper.getDefinitions(shortForm)
}