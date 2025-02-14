package com.bricklytics.pokesphere.uilayer.features.pokemondetails.model

import android.content.Context

sealed class PokemonDetailsEvents {
    data object OnBackPressed : PokemonDetailsEvents()
    data class OnLoadingView(
        val context: Context
    ) : PokemonDetailsEvents()
}