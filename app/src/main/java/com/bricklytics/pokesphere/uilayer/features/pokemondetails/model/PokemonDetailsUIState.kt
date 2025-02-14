package com.bricklytics.pokesphere.uilayer.features.pokemondetails.model

import androidx.compose.ui.graphics.Color
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel

data class PokemonDetailsUIState(
    val isLoading: Boolean = false,
    val colorTheme: Color = Color.Transparent,
    val pokemon: PokemonModel = PokemonModel(),
    val pokemonName: String = ""
)