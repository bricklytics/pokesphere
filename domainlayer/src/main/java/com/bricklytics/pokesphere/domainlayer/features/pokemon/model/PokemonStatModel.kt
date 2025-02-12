package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonStatModel(
    val baseStat: Int = 0,
    val effort: Int = 0,
    val stat: String = ""
)