package com.bricklytics.pokesphere.uilayer.features.pokemon.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.base.navigation.appNavigationManager
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonUI
import com.bricklytics.pokesphere.uilayer.features.pokemon.PokemonViewModel

fun NavGraphBuilder.pokemonNavigation(navController: NavController) {

    composable(AppRoutes.Pokemon.route) { bsEntry ->
        val viewModel = hiltViewModel<PokemonViewModel>()
        viewModel.setNavManager(
                appNavigationManager {
                    navControler = navController
                    backStackEntry = bsEntry
                }
            )

        PokemonUI(viewModel = viewModel)
    }
}