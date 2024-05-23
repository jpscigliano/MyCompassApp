package com.example.mycompassapp

import com.example.mycompassapp.data.repository.Repository
import com.example.mycompassapp.domain.usecase.WordCounterRequestUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class WordCounterTest {

    private val repository: Repository = mock()
    private val useCase = WordCounterRequestUseCase(repository, TestDispatcher())

    @Test
    fun `GIVEN an about Text WHEN WordCounterRequestUseCase is called THEN a Success character is return with valid Count of Characters`() = runTest {
        val mockedResponse = MockData.aboutText

        //GIVEN
        whenever(repository.getAboutText()).thenReturn(Result.success(mockedResponse))

        //WHEN
        val actual = useCase.execute().getOrNull()

        //THEN
        val expected: Map<Char, Int> = mapOf(
            '!' to 2, '?' to 1, 'T' to 2, 'a' to 2, 'b' to 1, 'e' to 3, 'h' to 2, 'i' to 4, 'm' to 1, 'n' to 2, 'o' to 3, 's' to 3, 't' to 6, 'u' to 1, 'w' to 1, 'x' to 2
        )
        assertEquals(expected, actual)
    }
}
