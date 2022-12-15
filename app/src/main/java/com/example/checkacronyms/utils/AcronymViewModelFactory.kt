package com.example.checkacronyms.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.checkacronyms.AcronymViewModel
import com.example.checkacronyms.db.MainRepository

class AcronymViewModelFactory(private val mainRepository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AcronymViewModel::class.java)) {
            return AcronymViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}