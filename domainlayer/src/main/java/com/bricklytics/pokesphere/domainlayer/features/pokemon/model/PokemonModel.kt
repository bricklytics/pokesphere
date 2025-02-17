package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonModel(
    val id: Int = 0,
    var page: Int = 0,
    val name: String = "",
    val baseExperience: Int = 0,
    val isFavorite: Boolean = false,
    val isShinny: Boolean = false,
    val officialArtworkModel: PokemonOfficialArtworkModel = PokemonOfficialArtworkModel(),
    val abilities: List<PokemonAbilityModel> = emptyList(),
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val types: List<PokemonTypeModel> = emptyList(),
    val stats: List<PokemonStatModel> = emptyList(),
    val moves: List<PokemonMoveModel> = emptyList()
)
