package com.example.borutoapp.domain.use_cases

import com.example.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.borutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.example.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.borutoapp.domain.use_cases.search_heroes.SearchHeroUseCase

data class UseCase(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val searchHeroUseCase: SearchHeroUseCase,
    val getSelectedHeroUseCase: GetSelectedHeroUseCase
)