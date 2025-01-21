package com.bricklytics.pokesphere.uilayer.features.pokemon.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonUI
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonViewModel
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonViewModelFactory

fun NavGraphBuilder.pokemonNavigation(navController: NavController) {

    composable(AppRoutes.Pokemon.route) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(AppRoutes.Home.route)
        }

        val viewModel = hiltViewModel<PokemonViewModel, PokemonViewModelFactory>(parentEntry) {
            it.create(
                name = backStackEntry.arguments?.getString("name").orEmpty()
            )
        }

        PokemonUI(
            navController = navController,
            viewModel = viewModel
        )
    }
}