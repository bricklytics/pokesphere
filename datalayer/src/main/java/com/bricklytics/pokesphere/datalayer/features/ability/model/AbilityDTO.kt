package com.bricklytics.pokesphere.datalayer.features.ability.model

import com.bricklytics.pokesphere.domainlayer.features.ability.model.AbilityModel
import com.google.gson.annotations.SerializedName

data class AbilityDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
) {
    fun mapTo() = AbilityModel(
        name = name.orEmpty(),
        url = url.orEmpty()
    )
}
