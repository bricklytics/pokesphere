package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonsModel
import com.google.gson.annotations.SerializedName

data class PokemonsDTO(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val pokemonList: List<PokemonDTO>?
) {
    fun mapTo() = PokemonsModel(
        count = count ?: 0,
        pokemonList = pokemonList.orEmpty().map { it.mapTo() }
    )
}
