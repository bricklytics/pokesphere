package com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase

import com.bricklytics.pokesphere.domainlayer.base.error.Failure
import com.bricklytics.pokesphere.domainlayer.base.error.FailureFactory
import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.base.usecase.BaseUseCase
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import javax.inject.Inject

class PokemonGetDataUseCase @Inject constructor(
    private val repository: PokemonRepository
) : BaseUseCase<ResultWrapper<PokemonModel, PokemonGetDataFailureFactory<ErrorDetailModel>>>() {

    override suspend fun fetch(
        args: Map<String, Any>
    ): ResultWrapper<PokemonModel, PokemonGetDataFailureFactory<ErrorDetailModel>> {
        return runAsync {
            if(args.isEmpty()) {
                return@runAsync ResultWrapper.createError(
                    PokemonGetDataFailureFactory.EmptyParamsFailure()
                )
            }
            repository.getPokemon(
                name = args["name"] as String
            ).transformError { PokemonGetDataFailureFactory() }
        }
    }
}

open class PokemonGetDataFailureFactory<ERROR_DATA> : FailureFactory<ERROR_DATA>() {

    class EmptyParamsFailure<ERROR_DATA> : PokemonGetDataFailureFactory<ERROR_DATA>()
    class GenericFailure<ERROR_DATA> : PokemonGetDataFailureFactory<ERROR_DATA>()

    override fun getFailure(code: Int): Failure<ERROR_DATA> {
        return when(code) {
            0 -> return GenericFailure()
            else -> GenericFailure()
        }
    }
}