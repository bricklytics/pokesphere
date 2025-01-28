package com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase

import com.bricklytics.pokesphere.domainlayer.base.error.model.ErrorDetailModel
import com.bricklytics.pokesphere.domainlayer.base.error.wrapper.ResultWrapper
import com.bricklytics.pokesphere.domainlayer.base.usecase.BaseUseCase
import com.bricklytics.pokesphere.domainlayer.features.PokemonRepository
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonsModel
import javax.inject.Inject

class PokemonGetListUseCase @Inject constructor(
    private val repository: PokemonRepository
) : BaseUseCase<ResultWrapper<PokemonsModel, PokemonGetListFailureFactory<ErrorDetailModel>>>() {

    override suspend fun fetch(
        args: Map<String, Any>
    ): ResultWrapper<PokemonsModel, PokemonGetListFailureFactory<ErrorDetailModel>> {
        return runAsync {
            if (args.isEmpty()) {
                return@runAsync ResultWrapper.Error(
                    PokemonGetListFailureFactory.EmptyParamsFailure()
                )
            }
            repository.getPokemonList(
                page = args["page"] as Int
            ).transformError {
                PokemonGetListFailureFactory<ErrorDetailModel>().createFailure(it)
            }.onSuccess { success ->
                if(success.pokemonList.isEmpty()){
                    return@runAsync ResultWrapper.Error(
                        PokemonGetListFailureFactory.EmptyDataFailure()
                    )
                }
            }
        }
    }
}

open class PokemonGetListFailureFactory<E> {

    class GenericFailure<E>(val body: E) : PokemonGetListFailureFactory<E>()
    class BaseFailure<E>(val code: Int) : PokemonGetListFailureFactory<E>()
    class EmptyParamsFailure<E> : PokemonGetListFailureFactory<E>()
    class EmptyDataFailure<E> : PokemonGetListFailureFactory<E>()

    fun createFailure(errorData: E): PokemonGetListFailureFactory<E> {
        return if (errorData is ErrorDetailModel) {
            translate(errorData)
        } else {
            GenericFailure(errorData)
        }
    }

    private fun <T> translate(
        errorData: T
    ): PokemonGetListFailureFactory<T> {
        val code = (errorData as ErrorDetailModel).code
        return when (code) {
            in 400..599 -> BaseFailure(errorData.code)
            else -> GenericFailure(errorData)
        }
    }
}

