package com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase

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
            if (args.isEmpty()) {
                return@runAsync ResultWrapper.Error(
                    PokemonGetDataFailureFactory.EmptyParamsFailure()
                )
            }

            repository.getPokemon(
                name = args["name"] as String,
                invalidateCache = args["invalidateCache"] as? Boolean ?: false
            ).transformError {
                PokemonGetDataFailureFactory<ErrorDetailModel>().createFailure(it)
            }
        }
    }
}

open class PokemonGetDataFailureFactory<E> {

    class GenericFailure<E>(val body: E) : PokemonGetDataFailureFactory<E>()
    class BaseFailure<E>(val code: Int) : PokemonGetDataFailureFactory<E>()
    class EmptyParamsFailure<E> : PokemonGetDataFailureFactory<E>()

    fun createFailure(errorData: E): PokemonGetDataFailureFactory<E> {
        return if (errorData is ErrorDetailModel) {
            translate(errorData)
        } else {
            GenericFailure(errorData)
        }
    }

    private fun <T> translate(
        errorData: T
    ): PokemonGetDataFailureFactory<T> {
        val code = (errorData as ErrorDetailModel).code
        return when (code) {
            in 400..599 -> BaseFailure(errorData.code)
            else -> GenericFailure(errorData)
        }
    }
}

