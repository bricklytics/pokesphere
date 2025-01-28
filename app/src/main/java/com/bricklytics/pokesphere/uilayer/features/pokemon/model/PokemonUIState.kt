package com.bricklytics.pokesphere.uilayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel

data class PokemonUIState(
    val isLoading: Boolean = false,
    val pokemon: PokemonModel = PokemonModel(),
    val pokemonList: MutableList<PokemonModel> = mutableListOf(),
    val page: Int = 0,
    val totalCount: Int  = 0,
    val pokemonName: String = "",
)
