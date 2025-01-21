package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.datalayer.features.ability.model.AbilityDTO
import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonAbilityModel
import com.google.gson.annotations.SerializedName

data class PokemonAbilityDTO(
    @SerializedName("ability")
    val ability: AbilityDTO?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?,
    @SerializedName("slot")
    val slot: Int?
) {
    fun mapTo() = PokemonAbilityModel(
        ability = ability?.mapTo() ?: AbilityModel(),
        isHidden = isHidden ?: false,
        slot = slot ?: 0
    )
}