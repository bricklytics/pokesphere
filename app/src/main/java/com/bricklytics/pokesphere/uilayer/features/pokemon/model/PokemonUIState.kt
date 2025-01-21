package com.bricklytics.pokesphere.uilayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel

data class PokemonUIState(
    val pokemon: PokemonModel = PokemonModel(),
    val pokemonName: String = ""
)
