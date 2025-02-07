package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonTypeModel
import com.google.gson.annotations.SerializedName

class PokemonTypeDTO(
    @SerializedName("slot")
    val slot: Int?,
    @SerializedName("type")
    val type: TypeDTO?
) {
    fun mapTo() = PokemonTypeModel(
        slot = slot ?: 0,
        type = type?.name.orEmpty()
    )
}

data class TypeDTO(
    @SerializedName("name")
    val name: String?,
)