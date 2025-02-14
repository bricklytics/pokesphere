package com.bricklytics.pokesphere.uilayer.features.pokemon

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataFailureFactory
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetListFailureFactory
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetListUseCase
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.SetFavoritePokemonUseCase
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.base.BaseViewModel
import com.bricklytics.pokesphere.uilayer.base.ResourcesProvider
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.model.BottomSheetModel
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.BottomSheetType
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.BottomSheetUIState
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonEvent
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonDataUseCase: PokemonGetDataUseCase,
    private val getPokemonListUseCase: PokemonGetListUseCase,
    private val setFavoritePokemonUseCase: SetFavoritePokemonUseCase,
    @AppDispatcher(AppDispatchers.IO)
    private val dispatcher: CoroutineDispatcher,
    private val resources: ResourcesProvider
) : BaseViewModel() {

    var uiState by mutableStateOf(PokemonUIState())
        private set

    var bottomSheetUiState by mutableStateOf(BottomSheetUIState())
        private set

    init {
        getPokemonList()
    }

    fun onEvent(event: PokemonEvent) {
        when (event) {
            is PokemonEvent.OnBackPressed -> {
                navigateBack()
            }

            is PokemonEvent.OnDismissBottomSheet -> {
                bottomSheetUiState = bottomSheetUiState.copy(
                    enabled = false
                )
            }

            is PokemonEvent.OnDrainedList -> {
                uiState = uiState.copy(page = uiState.page + 1)
                getPokemonList()
            }

            is PokemonEvent.OnLongPressCard -> {
                setFavoritePokemon(event.index)
            }

            is PokemonEvent.OnDoubleTapPokeCard -> {
                uiState = uiState.copy(isShinny = event.isShinny)
            }

            is PokemonEvent.OnTapPokeCard -> {
                navigateTo(
                    appRoutes = AppRoutes.PokemonDetails,
                    args = Json.encodeToString(uiState.pokemonList[event.index].name)
                )
            }
        }
    }

    @VisibleForTesting
    fun getPokemonList() {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            getPokemonListUseCase.fetch(
                args = mapOf(
                    "page" to uiState.page
                )
            ).onSuccess { success ->
                if(success.pokemonList.first().id == 0) {
                    val names = success.pokemonList
                        .map { it.name }
                        .toMutableList()
                    getPokemonByName(names)
                } else {
                    val list = uiState.pokemonList
                    uiState = uiState.copy(
                        isLoading = false,
                        pokemonList = list.apply {
                            addAll(success.pokemonList)
                        }
                    )
                }
            }.onFailure { error ->
                uiState = uiState.copy(isLoading = false)
                handlePokemonListError(error)
            }
        }
    }

    @VisibleForTesting
    fun setFavoritePokemon(index: Int) {
        viewModelScope.launch(dispatcher) {
            setFavoritePokemonUseCase.fetch(
                args = mapOf(
                    "name" to uiState.pokemonList[index].name,
                    "isShinny" to uiState.isShinny
                )
            ).onSuccess {
                getPokemonList()
            }
        }
    }

    @VisibleForTesting
    fun getPokemonByName(names: List<String>) {
        val pokemons = mutableListOf<PokemonModel>()
        names.forEach { name ->
            runBlocking(dispatcher) {
                getPokemonDataUseCase.fetch(
                    args = mapOf(
                        "name" to name
                    )
                ).onSuccess {
                    pokemons.add(it)
                }.onFailure { error ->
                    handlePokemonGetDataError(error)
                }
            }
        }
        uiState = uiState.copy(
            isLoading = false,
            pokemonList = pokemons.apply {
                addAll(0, uiState.pokemonList)
            }
        )
    }

    @VisibleForTesting
    fun <T> handlePokemonGetDataError(error: PokemonGetDataFailureFactory<T>) {
        when (error) {
            is PokemonGetDataFailureFactory.BaseFailure -> {
                bottomSheetUiState = bottomSheetUiState.copy(
                    enabled = true,
                    model = BottomSheetModel(
                        title = resources.getString(R.string.pokemon_bottom_sheet_title),
                        subTitle = resources.getString(R.string.pokemon_bottom_sheet_subtitle),
                        footer = error.code.toString(),
                        message = resources.getString(R.string.pokemon_bottom_sheet_description),
                    ),
                    errorType = BottomSheetType.Error
                )
            }
        }
    }

    @VisibleForTesting
    fun <T> handlePokemonListError(error: PokemonGetListFailureFactory<T>) {
        when (error) {
            is PokemonGetListFailureFactory.BaseFailure -> {
                bottomSheetUiState = bottomSheetUiState.copy(
                    enabled = true,
                    model = BottomSheetModel(
                        title = resources.getString(R.string.pokemon_bottom_sheet_title),
                        subTitle = resources.getString(R.string.pokemon_bottom_sheet_subtitle),
                        footer = error.code.toString(),
                        message = resources.getString(R.string.pokemon_bottom_sheet_description),
                    ),
                    errorType = BottomSheetType.Error
                )
            }
        }
    }
}