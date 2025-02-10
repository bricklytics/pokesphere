package com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.base.usecase.BaseUseCase
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import javax.inject.Inject

class SetFavoritePokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) : BaseUseCase<ResultWrapper<Boolean, SetFavoritePokemonFailureFactory<ErrorDetailModel>>>() {

    override suspend fun fetch(
        args: Map<String, Any>
    ): ResultWrapper<Boolean, SetFavoritePokemonFailureFactory<ErrorDetailModel>> {

        if (args.isEmpty() || !args.containsKey("name")
        ) {
            return ResultWrapper.Error(
                SetFavoritePokemonFailureFactory.EmptyParamsFailure()
            )
        }

        return runAsync {
            repository.setFavoritePokemon(
                name = args["name"] as String,
                isShinny = args["isShinny"] as Boolean
            ).transformError {
                SetFavoritePokemonFailureFactory<ErrorDetailModel>().createFailure(it)
            }
        }
    }
}

open class SetFavoritePokemonFailureFactory<E> {
    class GenericFailure<E>(val error: E) : SetFavoritePokemonFailureFactory<E>()
    class BaseFailure<E>(val code: Int) : SetFavoritePokemonFailureFactory<E>()
    class EmptyParamsFailure<E> : SetFavoritePokemonFailureFactory<E>()
    class DataBaseFailure<E>(val error: E) : SetFavoritePokemonFailureFactory<E>()

    fun createFailure(errorData: E): SetFavoritePokemonFailureFactory<E> {
        return if (errorData is ErrorDetailModel) {
            translate(errorData)
        } else {
            GenericFailure(errorData)
        }
    }

    private fun <T> translate(
        errorData: T
    ): SetFavoritePokemonFailureFactory<T> {
        val code = (errorData as ErrorDetailModel).code
        return when (code) {
            in 400..599 -> BaseFailure(errorData.code)
            1 -> DataBaseFailure(errorData)
            else -> GenericFailure(errorData)
        }
    }
}