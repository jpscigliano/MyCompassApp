package com.example.mycompassapp.presentation.compassScreen


sealed class Message {
    data object ShowUnableToLoadEvery10thToast : Message()
    data object ShowUnableToLoadWordCounterToast : Message()
}