package com.example.borutoapp.presentation.screens.splach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.borutoapp.domain.use_cases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
    private val _onBoardingComplete = MutableStateFlow(false)
    val onBoardingComplete = _onBoardingComplete.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingComplete.value =
                useCase.readOnBoardingUseCase().stateIn(viewModelScope).value
        }
    }

}