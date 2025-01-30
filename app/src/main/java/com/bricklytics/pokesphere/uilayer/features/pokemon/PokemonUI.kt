package com.bricklytics.pokesphere.uilayer.features.pokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.BottomSheet
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.ButtonOrientation
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCard
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCardSkeleton
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonEvent
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonUIState

@Composable
fun PokemonUI(navController: NavController, viewModel: PokemonViewModel) {

    var colorTheme by remember { mutableStateOf(Color.White) }

    Scaffold(
        topBar = {
            PokeTopBar(
                title = stringResource(R.string.pokemon_ui_title),
                color = colorTheme,
                onClickIcon = { navController.navigate(AppRoutes.Home.route) }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(it)
            ) {
                viewModel.bottomSheetUiState.let { bottomSheet ->
                    BottomSheet(
                        visible = bottomSheet.enabled,
                        model = bottomSheet.model,
                        buttonOrientaion = ButtonOrientation.Vertical,
                        primaryAction = stringResource(R.string.all_action_ok) to {
                            viewModel.onEvent(PokemonEvent.OnDismissBottomSheet)
                        },
                        onDismiss = { viewModel.onEvent(PokemonEvent.OnDismissBottomSheet) }
                    )
                }

                PokemonGridList(
                    uiState = viewModel.uiState,
                    onEvent = { viewModel.onEvent(PokemonEvent.OnDrainedList) }
                )
            }
        },
    )
}

@Composable
fun PokemonGridList(
    uiState: PokemonUIState,
    onEvent: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState.canScrollForward) {
        if (!lazyGridState.canScrollForward && !uiState.isLoading) {
            onEvent()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
    ) {
        items(uiState.pokemonList.size) { index ->
            if (uiState.isLoading) {
               repeat(6){
                    PokeCardSkeleton()
                }
            } else {
                PokeCard(
                    label = uiState.pokemonList[index].name,
                    imgUrl = uiState.pokemonList[index].officialArtworkModel.frontDefault,
                    onClick = {}
                )
            }
        }
    }
}