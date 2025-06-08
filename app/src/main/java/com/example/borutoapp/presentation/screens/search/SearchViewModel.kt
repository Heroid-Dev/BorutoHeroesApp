package com.example.borutoapp.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.use_cases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery=_searchQuery

    private val _searchHeroes=MutableStateFlow<PagingData<Hero>>(PagingData.empty())
    val searchHeroes=_searchHeroes

    fun updateSearchQuery(query:String){
        _searchQuery.value=query
    }

    fun getSearchHeroes(query:String){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.searchHeroUseCase(query = query).cachedIn(viewModelScope).collect{
                _searchHeroes.value=it
            }
        }
    }
}