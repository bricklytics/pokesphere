package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonMoveModel(
    val moveName: String = ""
)
