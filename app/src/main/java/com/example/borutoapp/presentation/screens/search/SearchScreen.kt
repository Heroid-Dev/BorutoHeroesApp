package com.example.borutoapp.presentation.screens.search

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.borutoapp.presentation.screens.common.ListContent

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {

    val searchQuery = searchViewModel.searchQuery.value

    val heroes = searchViewModel.searchHeroes.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onValueChanged = {
                    searchViewModel.updateSearchQuery(it)
                },
                onSearchClicked = {
                    searchViewModel.getSearchHeroes(it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->

        ListContent(heroes = heroes,
            navController = navController,
            paddingValues=paddingValues)
    }
}


@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(navController = rememberNavController())
}