package com.bricklytics.pokesphere.uilayer.features.pokemon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.model.BottomSheetModel
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataFailureFactory
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataUseCase
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.base.ResourcesProvider
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.BottomSheetType
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.BottomSheetUIState
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonEvent
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PokemonViewModelFactory::class)
class PokemonViewModel @AssistedInject constructor(
    @Assisted
    private val name: String,
    private val getPokemonData: PokemonGetDataUseCase,
    @AppDispatcher(AppDispatchers.IO)
    private val dispatcher: CoroutineDispatcher,
    private val resources: ResourcesProvider
) : ViewModel() {

    var uiState by mutableStateOf(PokemonUIState())
    private set

    var bottomSheetUiState by mutableStateOf(BottomSheetUIState())
        private set

    fun load() {
        viewModelScope.launch(dispatcher) {
            getPokemonData.fetch(
                args = mapOf(
                    "name" to name
                )
            ).onSuccess {
                uiState = uiState.copy(
                    pokemonName = name,
                    pokemon = it
                )
            }.onFailure { error ->
                when(error) {
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
        }
    }

    fun onEvent(event: PokemonEvent) {
        when (event) {
            is PokemonEvent.OnDismissBottomSheet -> {
                bottomSheetUiState = bottomSheetUiState.copy(
                    enabled = false
                )
            }
        }
    }
}

@AssistedFactory
interface PokemonViewModelFactory {
    fun create(name: String): PokemonViewModel
}