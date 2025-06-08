package com.example.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.borutoapp.presentation.screens.detail.DetailScreen
import com.example.borutoapp.presentation.screens.home.HomeScreen
import com.example.borutoapp.presentation.screens.search.SearchScreen
import com.example.borutoapp.presentation.screens.splach.SplashScreen
import com.example.borutoapp.presentation.screens.welcom.WelcomeScreen
import com.example.borutoapp.util.Constant.DETAIL_ARGUMENT_KEY


@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.name
    ) {

        composable(Screen.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(Screen.WelcomeScreen.name) {
                WelcomeScreen(navController = navController)
        }

        composable(Screen.HomeScreen.name) {
            HomeScreen(navController=navController)
        }

        composable(Screen.DetailScreen.name + "/{heroId}",
            arguments = listOf(
                navArgument(DETAIL_ARGUMENT_KEY) {
                    type = NavType.IntType
                }
            )) {

            DetailScreen(navController = navController)
        }

        composable(Screen.SearchScreen.name) {
            SearchScreen(navController)
        }
    }
}