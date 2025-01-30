package com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.base.usecase.BaseUseCase
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import javax.inject.Inject

class GetFavoritePokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) : BaseUseCase<ResultWrapper<PokemonModel, GetFavoritePokemonFailureFactory<ErrorDetailModel>>>() {

    override suspend fun fetch(
        args: Map<String, Any>
    ): ResultWrapper<PokemonModel, GetFavoritePokemonFailureFactory<ErrorDetailModel>> {
        return runAsync {
            repository
                .getFavoritePokemon()
                .transformError {
                    GetFavoritePokemonFailureFactory<ErrorDetailModel>().createFailure(it)
                }
        }
    }
}

open class GetFavoritePokemonFailureFactory<E> {
    class GenericFailure<E>(val error: E) : GetFavoritePokemonFailureFactory<E>()
    class BaseFailure<E>(val code: Int) : GetFavoritePokemonFailureFactory<E>()
    class DataBaseFailure<E>(val error: E) : GetFavoritePokemonFailureFactory<E>()

    fun createFailure(errorData: E): GetFavoritePokemonFailureFactory<E> {
        return if (errorData is ErrorDetailModel) {
            translate(errorData)
        } else {
            GenericFailure(errorData)
        }
    }

    private fun <T> translate(
        errorData: T
    ): GetFavoritePokemonFailureFactory<T> {
        val code = (errorData as ErrorDetailModel).code
        return when (code) {
            in 400..599 -> BaseFailure(errorData.code)
            1 -> DataBaseFailure(errorData)
            else -> GenericFailure(errorData)
        }
    }
}