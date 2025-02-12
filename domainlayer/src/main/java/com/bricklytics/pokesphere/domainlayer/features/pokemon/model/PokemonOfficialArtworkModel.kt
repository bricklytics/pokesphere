package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
class PokemonOfficialArtworkModel(
    val frontDefault: String = "",
    val frontShiny: String = ""
)