package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityModel
import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityModel(
    val ability: AbilityModel = AbilityModel(),
    val isHidden: Boolean = false,
    val slot: Int = 0,
)
