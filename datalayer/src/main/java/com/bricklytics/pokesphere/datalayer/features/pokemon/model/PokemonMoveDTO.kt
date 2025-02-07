package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonMoveModel
import com.google.gson.annotations.SerializedName

data class PokemonMoveDTO(
    @SerializedName("move")
    val move: MoveDTO?
) {
    fun mapTo() = PokemonMoveModel(
        moveName = move?.name.orEmpty()
    )
}

data class MoveDTO(
    @SerializedName("name")
    val name: String?
)