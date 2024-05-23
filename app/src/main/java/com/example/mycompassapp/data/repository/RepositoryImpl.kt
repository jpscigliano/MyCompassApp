package com.example.mycompassapp.data.repository

import com.example.mycompassapp.data.api.ApiService
import com.example.mycompassapp.domain.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val errorParser: ErrorParser,
    private val dispatcherProvider: DispatcherProvider,
) : Repository {

    override suspend fun getAboutText(): Result<String> = withContext(dispatcherProvider.io) {
        try {
            Result.success(apiService.getAboutPage())
        } catch (e: Throwable) {
            Result.failure(errorParser.parse(e))
        }
    }
}