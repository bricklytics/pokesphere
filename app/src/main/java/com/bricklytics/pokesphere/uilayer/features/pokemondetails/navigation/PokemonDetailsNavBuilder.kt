package com.bricklytics.pokesphere.uilayer.features.pokemondetails.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.base.navigation.appNavigationManager
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.PokemonDetailsUI
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.PokemonDetailsViewModel

fun NavGraphBuilder.pokemonDetailsNavigation(navController: NavController) {

    composable(
        route = "${AppRoutes.PokemonDetails.route}/{${AppRoutes.PokemonDetails.POKEMON_NAME}}",
        arguments = listOf(
            navArgument(AppRoutes.PokemonDetails.POKEMON_NAME) {
               this.type = NavType.StringType
            }
        ),
        enterTransition = {
            this.slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(1000),
            )
        }
    ) { bsEntry ->
        requireNotNull(bsEntry.arguments)

        val viewModel = hiltViewModel<PokemonDetailsViewModel>()

        viewModel.setNavManager(
            appNavigationManager {
                navControler = navController
                backStackEntry = bsEntry
            }
        )

        LaunchedEffect(navController) {
            viewModel.loadView()
        }
        PokemonDetailsUI(viewModel = viewModel)
    }
}