package com.example.borutoapp.data.repository

import androidx.paging.PagingData
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.repository.DataStoreOperation
import com.example.borutoapp.domain.repository.LocalDataSource
import com.example.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remote: RemoteDataSource,
    private val dataStoreOperation: DataStoreOperation,
) {

    fun getAllHeroes(): Flow<PagingData<Hero>> = remote.getAllHeroes()

    fun searchHeroes(query: String): Flow<PagingData<Hero>> = remote.searchHeroes(query = query)

    suspend fun getSelectedHero(heroId: Int): Hero {
        return localDataSource.getSelectedHero(heroId = heroId)
    }

    suspend fun saveOnBoardingState(complete: Boolean) {
        return dataStoreOperation.saveOnBoardingState(complete)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreOperation.readOnBoardingState()
    }

}



