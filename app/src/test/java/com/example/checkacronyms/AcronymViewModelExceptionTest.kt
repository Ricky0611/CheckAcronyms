package com.example.checkacronyms
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.checkacronyms.db.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import java.io.EOFException

@RunWith(MockitoJUnitRunner::class)
class AcronymViewModelExceptionTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var errorObserver: Observer<String>

    @Mock
    lateinit var repository: MainRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = RuntimeException::class)
    fun `test getAcronyms() suffers exception`() {
        runTest(StandardTestDispatcher()) {

            doThrow(RuntimeException("No Network.")).`when`(repository.getDefinitions("WHO"))

            val viewModel = AcronymViewModel(repository)

            viewModel.error.observeForever(errorObserver)

            viewModel.getDefinitions("WHO")
            val expectedResult = "Error: No Network."
            verify(repository).getDefinitions("WHO")
            verify(errorObserver).onChanged(expectedResult)
            viewModel.error.removeObserver(errorObserver)
        }
    }


}