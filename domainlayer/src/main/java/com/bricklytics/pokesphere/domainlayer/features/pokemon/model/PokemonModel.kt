package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

data class PokemonModel(
    val id: Int = 0,
    var page: Int = 0,
    val name: String = "",
    val baseExperience: Int = 0,
    val officialArtworkModel: PokemonOfficialArtworkModel = PokemonOfficialArtworkModel(),
    val abilities: List<PokemonAbilityModel> = emptyList(),
)
