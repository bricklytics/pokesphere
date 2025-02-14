package com.bricklytics.pokesphere.uilayer.base.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bricklytics.pokesphere.uilayer.features.home.navigation.homeNavigation
import com.bricklytics.pokesphere.uilayer.features.pokemon.navigation.pokemonNavigation
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.navigation.pokemonDetailsNavigation

class AppNavGraph {

    companion object {
        @Composable
        fun NavigationGraph(navController: NavHostController) {
            NavHost(
                navController = navController,
                startDestination = AppRoutes.Home.route
            ) {
                homeNavigation(navController)
                pokemonNavigation(navController)
                pokemonDetailsNavigation(navController)
            }
        }
    }
}