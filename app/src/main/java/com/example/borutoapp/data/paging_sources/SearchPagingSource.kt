package com.example.borutoapp.data.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.borutoapp.data.remote.BorutoApi
import com.example.borutoapp.domain.model.Hero


class SearchPagingSource(
    private val borutoApi: BorutoApi,
    private val query:String
):PagingSource<Int,Hero>() {
    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        return try {
            val apiResponse=borutoApi.searchHeroes(name = query)
            val heroes=apiResponse.heroes
            if(heroes.isNotEmpty()){
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            }else{
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}