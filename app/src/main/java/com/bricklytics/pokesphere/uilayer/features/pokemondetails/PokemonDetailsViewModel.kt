package com.bricklytics.pokesphere.uilayer.features.pokemondetails

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.PokemonGetDataUseCase
import com.bricklytics.pokesphere.uilayer.base.BaseViewModel
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.components.utils.PokemonBitmapCustomTarget
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.PokemonDetailsEvents
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.PokemonDetailsUIState
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDataUseCase: PokemonGetDataUseCase,
    @AppDispatcher(AppDispatchers.IO)
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {
    var uiState by mutableStateOf(PokemonDetailsUIState())
        private set

    fun loadView() {
        val json = getArgs(AppRoutes.PokemonDetails.POKEMON_NAME)
        uiState = uiState.copy(
            pokemonName = Json.decodeFromString(json)
        )
        getPokemon()
    }

    fun onEvent(event: PokemonDetailsEvents) {
        when (event) {
            is PokemonDetailsEvents.OnBackPressed -> {
                navigateBack()
            }

            is PokemonDetailsEvents.OnLoadingView -> {
                loadPokemonColorTheme(event.context)
            }
        }
    }

    @VisibleForTesting
    fun getPokemon() {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            getPokemonDataUseCase.fetch(
                args = mapOf(
                    "name" to uiState.pokemonName,
                    "invalidateCache" to true
                )
            ).onSuccess { success ->
                uiState = uiState.copy(
                    isLoading = false,
                    pokemon = success
                )
            }.onFailure {
                uiState = uiState.copy(isLoading = false)
//                handlePokemonGetDataError(it)
            }
        }
    }

    @VisibleForTesting
    fun loadPokemonColorTheme(context: Context) {
        viewModelScope.launch(dispatcher) {
            val primaryImg = uiState.pokemon.officialArtworkModel.frontDefault
            Glide.with(context)
                .asBitmap()
                .load(primaryImg)
                .into(PokemonBitmapCustomTarget { color ->
                    uiState = uiState.copy(primaryColorTheme = color)
                })
            val secondaryImg = uiState.pokemon.officialArtworkModel.frontShiny
            Glide.with(context)
                .asBitmap()
                .load(secondaryImg)
                .into(PokemonBitmapCustomTarget { color ->
                    uiState = uiState.copy(secondaryColorTheme = color)
                })
        }
    }
}