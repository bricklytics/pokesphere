package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeModel(
    val slot: Int = 0,
    val type: String = ""
)