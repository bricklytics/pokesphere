package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

data class PokemonsModel(
    val count: Int = 0,
    val pokemonList: List<PokemonModel> = emptyList()
)
