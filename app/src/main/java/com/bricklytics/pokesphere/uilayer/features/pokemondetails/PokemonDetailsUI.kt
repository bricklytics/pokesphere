package com.bricklytics.pokesphere.uilayer.features.pokemondetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.PokemonDetailsEvents
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.PokemonDetailsUIState

@Preview
@Composable
fun PokemonDetailsPreview() {
    MaterialTheme {
        PokemonDetailsUIContent(
            onEvent = {},
            uiState = PokemonDetailsUIState()
        )
    }
}

@Composable
fun PokemonDetailsUI(
    viewModel: PokemonDetailsViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel.uiState.pokemon) {
        if(viewModel.uiState.pokemon.name.isNotBlank()){
            viewModel.onEvent(PokemonDetailsEvents.OnLoadingView(context))
        }
    }

    PokemonDetailsUIContent(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent
    )

    BackHandler {
        viewModel.onEvent(PokemonDetailsEvents.OnBackPressed)
    }
}

@Composable
fun PokemonDetailsUIContent(
    uiState: PokemonDetailsUIState,
    onEvent: (PokemonDetailsEvents) -> Unit
) {
    Scaffold(
        topBar = {
            PokeTopBar(
                title = stringResource(R.string.pokemon_details_title),
                color = uiState.colorTheme,
                onClickIcon = { onEvent(PokemonDetailsEvents.OnBackPressed) },
            )
        },
        content = { paddingValues ->
            PokemonDetailsContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}

@Composable
fun PokemonDetailsContent(
    paddingValues: PaddingValues,
    uiState: PokemonDetailsUIState,
    onEvent: (PokemonDetailsEvents) -> Unit
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(uiState.pokemon.types.size){
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Magenta,
                                shape = RoundedCornerShape(50)
                            )
                    ){
                        Text(
                            text = uiState.pokemon.types[it].type,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}