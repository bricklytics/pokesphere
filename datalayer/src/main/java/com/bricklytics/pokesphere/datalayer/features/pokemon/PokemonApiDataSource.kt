package com.bricklytics.pokesphere.datalayer.features.pokemon

import androidx.annotation.WorkerThread
import com.bricklytics.pokesphere.datalayer.base.BaseApiDataSource
import com.bricklytics.pokesphere.datalayer.base.error.ErrorDetailDTO
import com.bricklytics.pokesphere.datalayer.features.pokemon.model.PokemonDTO
import com.bricklytics.pokesphere.datalayer.features.pokemon.model.PokemonsDTO
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@WorkerThread
class PokemonApiDataSource @Inject constructor (
    private val pokemonApi: PokemonApi,
    @AppDispatcher(AppDispatchers.IO)
    ioDispatcher: CoroutineDispatcher,
): BaseApiDataSource(ioDispatcher) {
    private val offset: Int = 20

    suspend fun getPokemon(
        name: String
    ): ResultWrapper<PokemonDTO, ErrorDetailDTO> {
        return safeApiCall {
            pokemonApi.getPokemon(name)
        }
    }

    suspend fun getPokemonList(
        page: Int
    ): ResultWrapper<PokemonsDTO, ErrorDetailDTO> {
        return safeApiCall {
            pokemonApi.getPokemonList(
                offset = page*offset,
                limit = offset
            )
        }
    }
}