package com.bricklytics.pokesphere.datalayer.features.pokemon.model

import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonsModel
import com.google.gson.annotations.SerializedName

data class PokemonsDTO(
    @SerializedName("results")
    val pokemonList: List<PokemonDTO>?
) {
    fun mapTo() = PokemonsModel(
        pokemonList = pokemonList.orEmpty().map { it.mapTo() }
    )
}
