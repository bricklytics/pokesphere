package com.bricklytics.pokesphere.datalayer.features.pokemon

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonsModel
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApiDataSource: PokemonApiDataSource
) : PokemonRepository {
    override suspend fun getPokemon(
        name: String
    ): ResultWrapper<PokemonModel, ErrorDetailModel> {
        return pokemonApiDataSource.getPokemon(name)
            .transformSuccess { it.mapTo() }
            .transformError { it.mapTo() }
    }

    override suspend fun getPokemonList(
        page: Int
    ): ResultWrapper<PokemonsModel, ErrorDetailModel> {
        return pokemonApiDataSource.getPokemonList(page)
            .transformSuccess { it.mapTo() }
            .transformError { it.mapTo() }
    }
}