package com.bricklytics.pokesphere.datalayer.features.ability.model

import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityModel
import com.google.gson.annotations.SerializedName

data class AbilityDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("effect_entries")
    val abilityEffects: List<AbilityEffectsDTO>
) {
    fun mapTo() = AbilityModel(
        name = name.orEmpty()
    )
}