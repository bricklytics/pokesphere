package com.bricklytics.pokesphere.uilayer.features.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.bricklytics.pokesphere.datalayer.network.AppDispatcher
import com.bricklytics.pokesphere.datalayer.network.AppDispatchers
import com.bricklytics.pokesphere.domainlayer.features.pokemon.usecase.GetFavoritePokemonUseCase
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.model.BottomBarItem
import com.bricklytics.pokesphere.uilayer.features.home.model.HomeEvents
import com.bricklytics.pokesphere.uilayer.features.home.model.HomeUIState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.guru.fontawesomecomposelib.FaIcons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFavoritePokemonUseCase: GetFavoritePokemonUseCase,
    @AppDispatcher(AppDispatchers.IO)
    private val dispatcher: CoroutineDispatcher
) : ViewModel(){

    var uiState by mutableStateOf(HomeUIState())
        private set

    init { loadView() }

    private fun loadView() {
        uiState = uiState.copy(
            bottomMenuItems = listOf(
                BottomBarItem(
                    resIcon = FaIcons.Home,
                    label = "Home"
                ),
                BottomBarItem(
                    resIcon = FaIcons.Search,
                    label = "Search"
                ),
            ),
            colorTheme = Color.Yellow
        )
        onEvent(HomeEvents.OnGetFavoritePokemon)
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.OnGetFavoritePokemon -> {
                uiState = uiState.copy(isLoading = true)
                getFavoritePokemon()
            }
            is HomeEvents.OnFetchPokemonImage -> {
                getPokemonImage(context = event.context)
            }
        }
    }

    @VisibleForTesting
    fun getFavoritePokemon() {
        viewModelScope.launch(dispatcher) {
            getFavoritePokemonUseCase
                .fetch()
                .onSuccess { success ->
                    uiState = uiState.copy(
                        favImage = success.officialArtworkModel.frontDefault
                    )
                }
                .onFailure { failure ->
                    uiState = uiState.copy(isLoading = false)
                }
        }
    }

    @VisibleForTesting
    fun getPokemonImage(context: Context) {
        viewModelScope.launch(dispatcher) {
            Glide.with(context)
                .asBitmap()
                .load(uiState.favImage)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        Palette.from(resource).generate { pallete ->
                            pallete?.let {
                                val dominantColor = it.getDominantColor(
                                    Color(0xFF000000).toArgb()
                                )
                                uiState = uiState.copy(
                                    colorTheme = Color(dominantColor)
                                )
                            }
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle cleanup if needed
                    }
                })
        }
    }
}