package com.bricklytics.pokesphere.uilayer.features.pokemondetails.model

import android.content.Context

sealed interface PokemonDetailsEvents {
    data object OnBackPressed : PokemonDetailsEvents
    data class OnLoadingView(
        val context: Context
    ) : PokemonDetailsEvents
}