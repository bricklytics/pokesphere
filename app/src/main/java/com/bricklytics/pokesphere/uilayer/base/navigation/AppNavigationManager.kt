package com.bricklytics.pokesphere.uilayer.base.navigation

import androidx.navigation.NavHostController

class AppNavigationManager(
    private val navController: NavHostController
) {
    fun navigateTo(route: String) {
        navController.navigate(route)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}