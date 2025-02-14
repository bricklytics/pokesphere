package com.bricklytics.pokesphere.domainlayer.features

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonsModel

interface PokemonRepository {

    suspend fun getPokemon(
        name: String,
        invalidateCache: Boolean
    ): ResultWrapper<PokemonModel, ErrorDetailModel>

    suspend fun getPokemonList(
        page: Int
    ): ResultWrapper<PokemonsModel, ErrorDetailModel>

    suspend fun setFavoritePokemon(
        name: String,
        isShinny: Boolean
    ): ResultWrapper<Boolean, ErrorDetailModel>

    suspend fun getFavoritePokemon(): ResultWrapper<PokemonModel, ErrorDetailModel>
}