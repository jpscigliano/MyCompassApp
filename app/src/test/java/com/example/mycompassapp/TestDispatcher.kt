package com.example.mycompassapp

import com.example.mycompassapp.domain.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcher : DispatcherProvider {
    override val main = UnconfinedTestDispatcher()
    override val default = UnconfinedTestDispatcher()
    override val io = UnconfinedTestDispatcher()
    override val unconfined = UnconfinedTestDispatcher()
}