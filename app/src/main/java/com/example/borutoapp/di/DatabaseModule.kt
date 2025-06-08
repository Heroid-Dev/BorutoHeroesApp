package com.example.borutoapp.di

import android.content.Context
import androidx.room.Room
import com.example.borutoapp.data.HeroDatabase
import com.example.borutoapp.data.repository.LocalDataSourceImpl
import com.example.borutoapp.domain.repository.LocalDataSource
import com.example.borutoapp.util.Constant.BORUTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): HeroDatabase {
        return Room.databaseBuilder(
            context = context,
            name = BORUTO_DATABASE,
            klass = HeroDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        heroDatabase: HeroDatabase,
    ): LocalDataSource {
        return LocalDataSourceImpl(heroDatabase)
    }

}