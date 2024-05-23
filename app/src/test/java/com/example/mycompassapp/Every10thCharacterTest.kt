package com.example.mycompassapp

import com.example.mycompassapp.data.repository.Repository
import com.example.mycompassapp.domain.usecase.Every10thCharacterRequestUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class Every10thCharacterTest {

    private val repository: Repository = mock()
    private val useCase = Every10thCharacterRequestUseCase(repository, TestDispatcher())

    @Test
    fun `GIVEN an about Text WHEN Every10thCharacterRequestUseCase is called THEN a Success character is return with valid Characters`() = runTest {
        val mockedResponse = MockData.aboutText

        //GIVEN
        whenever(repository.getAboutText()).thenReturn(Result.success(mockedResponse))

        //WHEN
        val actual = useCase.execute().getOrNull()

        //THEN
        val expected = listOf("b", "w", "x")
        assertEquals(expected, actual)
    }
}
