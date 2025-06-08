package com.example.borutoapp.di

import android.content.Context
import com.example.borutoapp.data.repository.DataStoreOperationImpl
import com.example.borutoapp.data.repository.Repository
import com.example.borutoapp.domain.repository.DataStoreOperation
import com.example.borutoapp.domain.use_cases.UseCase
import com.example.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.example.borutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.example.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.borutoapp.domain.use_cases.search_heroes.SearchHeroUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperation(@ApplicationContext context: Context): DataStoreOperation {
        return DataStoreOperationImpl(context)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: Repository): UseCase {
        return UseCase(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroUseCase = SearchHeroUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository)
        )
    }
}