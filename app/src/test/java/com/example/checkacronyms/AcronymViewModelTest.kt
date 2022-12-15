package com.example.checkacronyms

import org.junit.Assert.*

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.checkacronyms.data.AcronymsResponse
import com.example.checkacronyms.data.LongForm
import com.example.checkacronyms.db.MainRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class AcronymViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var longFormListObserver: Observer<List<LongForm>>

    @Mock
    lateinit var repository: MainRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getDefinitions() returns valid data`() {
        runTest(StandardTestDispatcher()) {

            val gson = Gson()
            val typeToken = object : TypeToken<List<AcronymsResponse>>() {}
            val mockResponse = Response.success(gson.fromJson(Constants.SUCCESS_RESPONSE, typeToken))

            Mockito.`when`(repository.getDefinitions("WHO")).thenReturn(mockResponse)

            val viewModel = AcronymViewModel(repository)

            viewModel.longFormList.observeForever(longFormListObserver)

            viewModel.getDefinitions("WHO")
            val expectedResult = gson.fromJson(Constants.SUCCESS_RESPONSE, typeToken)[0].lfs
            verify(repository).getDefinitions("WHO")

            verify(longFormListObserver).onChanged(expectedResult)

            viewModel.longFormList.removeObserver(longFormListObserver)
        }
    }
}
