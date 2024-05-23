package com.example.mycompassapp.domain.domain

sealed class AppError : Throwable() {
    sealed class ApiError : AppError() {
        data object TimeOut : AppError()
    }

    data object UnableToGetAboutText : AppError()
    data object UnknownError : AppError()

}