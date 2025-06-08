package com.example.borutoapp.data.repository

import com.example.borutoapp.data.HeroDatabase
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    private val heroDatabase: HeroDatabase
):LocalDataSource {

    private val heroDao=heroDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }
}