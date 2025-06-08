package com.example.borutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.borutoapp.domain.use_cases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UseCase
):ViewModel() {
    val getAllHeroes=useCase.getAllHeroesUseCase()
}