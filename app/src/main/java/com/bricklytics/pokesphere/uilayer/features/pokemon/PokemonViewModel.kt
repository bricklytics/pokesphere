package com.bricklytics.pokesphere.uilayer.features.pokemon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataUseCase
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonUIState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = PokemonViewModelFactory::class)
class PokemonViewModel @AssistedInject constructor(
    @Assisted private val name: String,
    private val getPokemonData: PokemonGetDataUseCase,
    @AppDispatcher(AppDispatchers.IO)
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    var uiState by mutableStateOf(PokemonUIState())
    private set

    fun load() {
        viewModelScope.launch(dispatcher) {
            getPokemonData.fetch(
                args = mapOf(
                    "name" to name
                )
            ).transformSuccess {
                uiState = uiState.copy(
                    pokemonName = name,
                    pokemon = it
                )
            }
        }
    }
}

@AssistedFactory
interface PokemonViewModelFactory {
    fun create(name: String): PokemonViewModel
}