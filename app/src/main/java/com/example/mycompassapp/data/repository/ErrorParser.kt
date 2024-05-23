package com.example.mycompassapp.data.repository

import com.example.mycompassapp.domain.domain.AppError

interface ErrorParser {
    fun parse(throwable: Throwable): AppError
}