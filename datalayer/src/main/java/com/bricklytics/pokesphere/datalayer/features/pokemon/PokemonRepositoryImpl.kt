package com.bricklytics.pokesphere.datalayer.features.pokemon

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonsModel
import com.bricklytics.pokesphere.localdatalayer.feature.pokemon.PokemonDao
import com.bricklytics.pokesphere.localdatalayer.feature.pokemon.asDomain
import com.bricklytics.pokesphere.localdatalayer.feature.pokemon.asEntity
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApiDataSource: PokemonApiDataSource,
    private val pokemonDao: PokemonDao
) : PokemonRepository {
    override suspend fun getPokemon(
        name: String
    ): ResultWrapper<PokemonModel, ErrorDetailModel> {
        val daoData = pokemonDao
            .getPokemon(name)
            .asDomain()

        if (daoData != null) {
            return ResultWrapper.Success(data = daoData)
        }

        return pokemonApiDataSource
            .getPokemon(name)
            .transformSuccess { it.mapTo() }
            .transformError { it.mapTo() }
            .onSuccess {
                pokemonDao.insertPokemon(it.asEntity())
            }

//        return pokemonApiDataSource.getPokemon(name)
//            .transformSuccess { it.mapTo() }
//            .transformError { it.mapTo() }
    }

    override suspend fun getPokemonList(
        page: Int
    ): ResultWrapper<PokemonsModel, ErrorDetailModel> {
        val daoData = pokemonDao
            .getPokemonList(page)
            .mapNotNull { it.asDomain() }

        if (daoData.isNotEmpty()) {
          return ResultWrapper.Success(
                data = PokemonsModel(pokemonList = daoData)
            )
        }

        return pokemonApiDataSource
            .getPokemonList(page)
            .transformSuccess { it.mapTo() }
            .transformError { it.mapTo() }
            .onSuccess { success ->
                if(success.pokemonList.isNotEmpty()) {
                    val pokemons = success.pokemonList.map { model ->
                        model.also { it.page = page }.asEntity()
                    }
                    pokemonDao.insertPokemons(pokemons)
                }
            }
//        return pokemonApiDataSource.getPokemonList(page)
//            .transformSuccess { it.mapTo() }
//            .transformError { it.mapTo() }
    }

    override suspend fun setFavoritePokemon(
        name: String,
        favorite: Boolean
    ): ResultWrapper<Boolean, ErrorDetailModel> {
        return runCatching {
            pokemonDao.setFavoritePokemon(name, favorite)
        }.fold(
            onSuccess = {
                ResultWrapper.Success(data = true)
            },
            onFailure = {
                ResultWrapper.Error(
                    ErrorDetailModel(
                        code = 1,
                        message = it.message.orEmpty()
                    )
                )
            }
        )
//        return ResultWrapper.Error(
//            ErrorDetailModel(
//                code = 1,
//                message = "Database not implmemented"
//            )
//        )
    }

    override suspend fun getFavoritePokemon(): ResultWrapper<PokemonModel, ErrorDetailModel> {
        return runCatching {
            pokemonDao.getFavoritePokemon()
                .asDomain()
                .also {
                    if (it == null) throw Throwable("Favorite pokemon not found")
                }
        }.fold(
            onSuccess = {
                ResultWrapper.Success(data = it!!)
            },
            onFailure = {
                ResultWrapper.Error(
                    ErrorDetailModel(
                        code = 1,
                        message = it.cause?.message.orEmpty()
                    )
                )
            }
        )

//        return ResultWrapper.Error(
//            ErrorDetailModel(
//                code = 1,
//                message = "Database not implmemented"
//            )
//        )
    }
}