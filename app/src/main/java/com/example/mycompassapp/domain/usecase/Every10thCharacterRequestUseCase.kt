package com.example.mycompassapp.domain.usecase

import com.example.mycompassapp.data.repository.Repository
import com.example.mycompassapp.domain.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Every10thCharacterRequestUseCase @Inject constructor(
    private val repository: Repository,
    private val dispatcherProvider: DispatcherProvider,
) {

    suspend fun execute(): Result<List<Char>> = withContext(dispatcherProvider.io) {
        repository.getAboutText()
            .map { text ->
                text.filter { !it.isWhitespace() }
                    .filterIndexed { index, _ -> (index + 1) % 10 == 0 }
                    .map { it }
            }
    }
}