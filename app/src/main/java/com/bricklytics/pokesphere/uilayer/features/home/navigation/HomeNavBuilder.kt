package com.bricklytics.pokesphere.uilayer.features.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.base.navigation.appNavigationManager
import com.bricklytics.pokesphere.uilayer.features.home.HomeUI
import com.bricklytics.pokesphere.uilayer.features.home.HomeViewModel

fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable(AppRoutes.Home.route) { bsEntry ->
        val viewModel = hiltViewModel<HomeViewModel>()

        viewModel.setNavManager(
            appNavigationManager {
                navControler = navController
                backStackEntry = bsEntry
            }
        )

        HomeUI(viewModel = viewModel)
    }
}