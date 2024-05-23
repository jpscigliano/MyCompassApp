package com.example.mycompassapp.data.repository

import com.example.mycompassapp.domain.domain.AppError
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ErrorParserImpl @Inject constructor() : ErrorParser {
    override fun parse(throwable: Throwable): AppError {
        return when (throwable) {
            is SocketTimeoutException -> AppError.ApiError.TimeOut
            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                // Assuming you have a way to parse the error body into a specific ApiError
                parseHttpError(errorBody) ?: AppError.UnknownError
            }

            else -> AppError.UnknownError
        }
    }

    // Helper function to parse the error body
    private fun parseHttpError(errorBody: String?): AppError {
        // Implement your logic to parse the error body here
        // In case we have specific errors we  might look for the specific error codes or messages
        return when (errorBody) {
            "specific_error_code" -> AppError.UnableToGetAboutText
            // Add more cases as needed
            else -> AppError.UnknownError
        }
    }

}