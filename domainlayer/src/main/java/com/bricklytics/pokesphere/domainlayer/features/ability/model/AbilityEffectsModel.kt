package com.bricklytics.pokesphere.domainlayer.features.ability.model

data class AbilityEffectsModel(
    val effect: String = "",
    val language: AbilityLanguageModel = AbilityLanguageModel(),
    val shortEffect: String = ""
)

data class AbilityLanguageModel(
    val name: String = ""
)
