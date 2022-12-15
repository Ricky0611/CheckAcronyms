package com.example.checkacronyms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkacronyms.data.LongForm
import com.example.checkacronyms.db.MainRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class AcronymViewModel(val mainRepository: MainRepository): ViewModel() {

    private val longForms = MutableLiveData<List<LongForm>>()
    val longFormList: LiveData<List<LongForm>> = longForms
    val error = MutableLiveData<String>()

    fun getDefinitions(shortForm: String) {
        viewModelScope.launch(IO) {
            try {
                val response = mainRepository.getDefinitions(shortForm)
                if (response.isSuccessful) {
                    if (response.body()?.isEmpty() != true) {
                        longForms.postValue(response.body()?.get(0)?.lfs)
                    } else {
                        longForms.postValue(emptyList())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error.postValue("Error: $e")
            }
        }
    }
}