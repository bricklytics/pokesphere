package com.bricklytics.pokesphere.uilayer.base.navigation

sealed class AppRoutes(val route: String) {

    data object Home : AppRoutes("home")
    data object Pokemon : AppRoutes("pokemon")
    data object PokemonDetails : AppRoutes("pokemon-details"){
        const val POKEMON_NAME = "name"
    }
}