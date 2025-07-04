package com.example.borutoapp.domain.use_cases.save_onboarding

import com.example.borutoapp.data.repository.Repository

class SaveOnBoardingUseCase(private val repository: Repository) {
    suspend operator fun invoke(complete: Boolean) {
        repository.saveOnBoardingState(complete = complete)
    }
}