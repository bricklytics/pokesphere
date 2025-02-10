package com.bricklytics.pokesphere.uilayer.features.pokemon

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.BottomSheet
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.ButtonOrientation
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCard
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCardSkeleton
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.BottomSheetUIState
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonEvent
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonUIState

@Composable
@Preview(showSystemUi = true, device = Devices.PIXEL_6)
private fun PokemonUIPreview() {
    MaterialTheme {
        PokemonUIContent(
            uiState = PokemonUIState(),
            bottomSheetUiState = BottomSheetUIState(),
            onEvent = {},
        )
    }
}


@Composable
fun PokemonUI(
    viewModel: PokemonViewModel
) {
    PokemonUIContent(
        uiState = viewModel.uiState,
        bottomSheetUiState = viewModel.bottomSheetUiState,
        onEvent = viewModel::onEvent,
    )

    BackHandler {
        viewModel.onEvent(PokemonEvent.OnBackPressed)
    }
}

@Composable
fun PokemonUIContent(
    uiState: PokemonUIState,
    bottomSheetUiState: BottomSheetUIState,
    onEvent: (PokemonEvent) -> Unit,
) {
    val colorTheme by remember { mutableStateOf(Color.White) }

    Scaffold(
        topBar = {
            PokeTopBar(
                title = stringResource(R.string.pokemon_ui_title),
                color = colorTheme,
                onClickIcon = { onEvent(PokemonEvent.OnBackPressed) }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(it)
            ) {
                bottomSheetUiState.let { bottomSheet ->
                    BottomSheet(
                        visible = bottomSheet.enabled,
                        model = bottomSheet.model,
                        buttonOrientaion = ButtonOrientation.Vertical,
                        primaryAction = stringResource(R.string.all_action_ok) to {
                            onEvent(PokemonEvent.OnDismissBottomSheet)
                        },
                        onDismiss = { onEvent(PokemonEvent.OnDismissBottomSheet) }
                    )
                }

                PokemonGridList(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }
        },
    )
}

@Composable
fun PokemonGridList(
    uiState: PokemonUIState,
    onEvent: (PokemonEvent) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState.canScrollForward) {
        if (!lazyGridState.canScrollForward && lazyGridState.canScrollBackward && !uiState.isLoading) {
            onEvent(PokemonEvent.OnDrainedList)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
    ) {
        uiState.pokemonList.let {
            if (it.isNotEmpty()) {
                items(it.size) { index ->
                    PokeCard(
                        label = "#${it[index].id} ${it[index].name}",
                        primaryImgUrl = it[index].officialArtworkModel.frontDefault,
                        secondaryImgUrl = it[index].officialArtworkModel.frontShiny,
                        isFavorite = it[index].isFavorite,
                        isFlipped = it[index].isShinny,
                        onDoubleTap = { flipped ->
                            onEvent(PokemonEvent.OnDoubleTapPokeCard(index, flipped))
                        },
                        onLongPress = {
                            onEvent(PokemonEvent.OnLongPressCard(index))
                        }
                    )
                }
            } else {
                items(6) { PokeCardSkeleton() }
            }
        }
    }
}