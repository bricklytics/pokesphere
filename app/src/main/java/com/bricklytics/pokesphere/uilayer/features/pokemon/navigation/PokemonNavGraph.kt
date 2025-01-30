package com.bricklytics.pokesphere.uilayer.features.pokemon.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonUI
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonViewModel

fun NavGraphBuilder.pokemonNavigation(navController: NavController) {

    composable(AppRoutes.Pokemon.route) { backStackEntry ->
        val viewModel = hiltViewModel<PokemonViewModel>()

        PokemonUI(
            navController = navController,
            viewModel = viewModel
        )
    }
}