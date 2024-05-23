package com.example.mycompassapp.data.repository

interface Repository {
    suspend fun getAboutText(): Result<String>
}