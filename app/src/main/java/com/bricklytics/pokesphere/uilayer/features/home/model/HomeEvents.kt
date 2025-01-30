package com.bricklytics.pokesphere.uilayer.features.home.model

import android.content.Context

sealed class HomeEvents {
    data object OnGetFavoritePokemon : HomeEvents()

    data class OnFetchPokemonImage(
        val context: Context
    ) : HomeEvents()
}