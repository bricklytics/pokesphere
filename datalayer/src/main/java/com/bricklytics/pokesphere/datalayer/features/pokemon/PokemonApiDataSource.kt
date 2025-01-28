package com.bricklytics.pokesphere.datalayer.features.pokemon

import androidx.annotation.WorkerThread
import com.bricklytics.pokesphere.datalayer.base.BaseApiDataSource
import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.features.pokemon.model.PokemonDTO
import com.bricklytics.pokesphere.datalayer.features.pokemon.model.PokemonsDTO
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import javax.inject.Inject

@WorkerThread
class PokemonApiDataSource @Inject constructor (
    private val pokemonAPI: PokemonAPI,
): BaseApiDataSource() {
    private val offset: Int = 20

    suspend fun getPokemon(
        name: String
    ): ResultWrapper<PokemonDTO, ErrorDetailDTO> {
        return safeApiCall {
            pokemonAPI.getPokemon(name)
        }
    }

    suspend fun getPokemonList(
        page: Int
    ): ResultWrapper<PokemonsDTO, ErrorDetailDTO> {
        return safeApiCall {
            pokemonAPI.getPokemonList(
                offset = page*offset,
                limit = offset
            )
        }
    }
}