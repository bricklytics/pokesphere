package com.bricklytics.pokesphere.domainlayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityModel

data class PokemonAbilityModel(
    val ability: AbilityModel = AbilityModel(),
    val isHidden: Boolean = false,
    val slot: Int = 0,
)
