package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

data class PokemonModel(
    val abilities: List<PokemonAbilityModel> = emptyList(),
    val baseExperience: Int = 0
)
