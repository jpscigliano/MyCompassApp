package com.example.mycompassapp.domain.usecase

import com.example.mycompassapp.data.repository.Repository
import com.example.mycompassapp.domain.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordCounterRequestUseCase @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider,
) {

    suspend fun execute(): Result<Map<Char, Int>> = withContext(dispatcherProvider.io) {
        repository.getAboutText().map { text ->
            text.filter { !it.isWhitespace() }
                .groupingBy { it }
                .fold(0) { count, _ -> count + 1 }
                .toSortedMap()
        }
    }
}