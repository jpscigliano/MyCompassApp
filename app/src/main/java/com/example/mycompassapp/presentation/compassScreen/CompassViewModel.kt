package com.example.mycompassapp.presentation.compassScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycompassapp.domain.usecase.Every10thCharacterRequestUseCase
import com.example.mycompassapp.domain.usecase.WordCounterRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompassViewModel @Inject constructor(
    private val every10thCharacterRequestUseCase: Every10thCharacterRequestUseCase,
    private val wordCounterRequestUseCase: WordCounterRequestUseCase
) : ViewModel() {

    private val mutableViewState = MutableStateFlow(CompassViewState())
    val viewState: StateFlow<CompassViewState> = mutableViewState

    private val mutableMessage: MutableSharedFlow<Message> = MutableSharedFlow()
      val messages: SharedFlow<Message> = mutableMessage

    fun onRunButtonClicked() {
        launchWordCount()
        launchEvey10thCharacter()
    }

    private fun launchEvey10thCharacter() = viewModelScope.launch {
        mutableViewState.update { it.copy(isCharactersRequestLoading = true) }
        val charactersResult = every10thCharacterRequestUseCase.execute()
        mutableViewState.update {
            it.copy(
                isCharactersRequestLoading = false,
                characters = charactersResult.getOrNull() ?: listOf()
            )
        }
        if (charactersResult.isFailure) {
            mutableMessage.emit(Message.ShowUnableToLoadWordCounterToast)
        }
    }

    private fun launchWordCount() = viewModelScope.launch {
        mutableViewState.update { it.copy(isOccurrenceRequestLoading = true) }
        val wordCounterResult = wordCounterRequestUseCase.execute()
        mutableViewState.update {
            it.copy(
                isOccurrenceRequestLoading = false,
                occurrenceCount = wordCounterResult.getOrNull() ?: mapOf()
            )
        }
        if (wordCounterResult.isFailure) {
            mutableMessage.emit(Message.ShowUnableToLoadWordCounterToast)
        }
    }
}