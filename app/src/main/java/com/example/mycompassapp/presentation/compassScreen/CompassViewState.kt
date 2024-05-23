package com.example.mycompassapp.presentation.compassScreen

import androidx.compose.runtime.Immutable

@Immutable
data class CompassViewState(
    val isCharactersRequestLoading: Boolean = false,
    val characters: List<Char> = listOf(),
    val isOccurrenceRequestLoading: Boolean = false,
    val occurrenceCount: Map<Char, Int> = mapOf()
)
