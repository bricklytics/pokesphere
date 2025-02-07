package com.bricklytics.pokesphere.datalayer.features.ability.model

import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityEffectsModel
import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityLanguageModel
import com.google.gson.annotations.SerializedName

data class AbilityEffectsDTO(
    @SerializedName("effect")
    val effect: String?,
    @SerializedName("language")
    val language: AbilityLanguageDTO,
    @SerializedName("short_effect")
    val shortEffect: String?
) {
    fun mapTo() = AbilityEffectsModel(
        effect = effect.orEmpty(),
        language = language.mapTo(),
        shortEffect = shortEffect.orEmpty()
    )
}

data class AbilityLanguageDTO(
    @SerializedName("name")
    val name: String?
) {
    fun mapTo() = AbilityLanguageModel(
        name = name.orEmpty()
    )
}
