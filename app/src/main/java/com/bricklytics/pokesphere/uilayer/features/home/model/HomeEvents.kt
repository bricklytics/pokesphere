package com.bricklytics.pokesphere.uilayer.features.home.model

import android.content.Context

sealed interface HomeEvents {
    data object  OnBackPressed : HomeEvents
    data object OnGetFavoritePokemon : HomeEvents
    data object OnHomePressed : HomeEvents
    data object OnSearchPokemon : HomeEvents

    data class OnFetchPokemonImage(
        val context: Context
    ) : HomeEvents
}