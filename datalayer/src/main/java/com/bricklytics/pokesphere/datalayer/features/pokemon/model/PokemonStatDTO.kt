package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonStatModel
import com.google.gson.annotations.SerializedName

class PokemonStatDTO (
    @SerializedName("base_stat")
    val baseStat: Int?,
    @SerializedName("effort")
    val effort: Int?,
    @SerializedName("stat")
    val statType: PokemonStatTypeDTO?
) {
    fun mapTo() = PokemonStatModel(
        baseStat = baseStat ?: 0,
        effort = effort ?: 0,
        stat = statType?.name.orEmpty()
    )
}

class PokemonStatTypeDTO (
    @SerializedName("name")
    val name: String?
)