package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    @SerializedName("abilities")
    val abilities: List<PokemonAbilityDTO>?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
) {
    fun mapTo() = PokemonModel(
        abilities = abilities.orEmpty().map{ it.mapTo() },
        baseExperience = baseExperience ?: 0
    )
}