package com.example.borutoapp.data.paging_sources

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.borutoapp.data.HeroDatabase
import com.example.borutoapp.data.remote.BorutoApi
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.model.HeroRemoteKeys
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator(
    private val borutoApi: BorutoApi,
    private val heroDatabase: HeroDatabase,
) : RemoteMediator<Int, Hero>() {

    private val heroDao = heroDatabase.heroDao()
    private val heroRemoteKeysDao = heroDatabase.heroRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdate = heroRemoteKeysDao.getRemoteKeys(heroId = 1)?.lastUpdate ?: 0L
        val cacheTime = 1440

        val differentTime = (currentTime - lastUpdate) / 1000 / 60

        Log.d("RemoteMediator", "Current Time: ${pressMiles(currentTime)}")
        Log.d("RemoteMediator", "Last Update: ${pressMiles(lastUpdate)}")
        Log.d("RemoteMediator", "Last Update: ${differentTime.toInt()}")



        return if (differentTime.toInt() <= cacheTime) {
            Log.d("RemoteMediator", "initialize: UP TO DATE!")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("RemoteMediator", "initialize: REFRESH!")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeysClosestTOCurrentPosition(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(heroId = id)
            }
        }
    }

    private suspend fun getRemoteKeysToFirstItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
        }
    }


    private suspend fun getRemoteKeysToLastItem(state: PagingState<Int, Hero>): HeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeysDao.getRemoteKeys(heroId = hero.id)
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosestTOCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeysToFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeysToLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                if (loadType == LoadType.REFRESH) {
                    heroDao.deleteAllHeroes()
                    heroRemoteKeysDao.deleteAllRemoteKeys()
                }
                val prevPage = response.prevPage
                val nextPage = response.nextPage
                val key = response.heroes.map { hero ->
                    HeroRemoteKeys(
                        id = hero.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        lastUpdate = response.lastUpdate
                    )
                }
                heroDao.addHeroes(response.heroes)
                heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys = key)
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun pressMiles(miles: Long): String {
        val date = Date(miles)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
        return format.format(date)
    }
}