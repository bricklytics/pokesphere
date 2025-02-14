package com.bricklytics.pokesphere.uilayer.features.pokemondetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.components.fonts.psFontFamily
import com.bricklytics.pokesphere.uilayer.components.utils.getContrastingColor
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.PokemonDetailsEvents
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.PokemonDetailsUIState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

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
        if (viewModel.uiState.pokemon.name.isNotBlank()) {
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
                color = uiState.primaryColorTheme,
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(paddingValues)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PokemonDetailHeader(
                uiState = uiState,
                modifier = Modifier
                    .background(
                        brush = gradient(
                            isShinny = uiState.pokemon.isShinny,
                            colors = listOf(
                                uiState.primaryColorTheme,
                                uiState.secondaryColorTheme
                            )
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = 48.dp,
                            bottomEnd = 48.dp
                        )
                    )
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(uiState.pokemon.types.size) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Magenta,
                                shape = RoundedCornerShape(32.dp)
                            )
                    ) {
                        Text(
                            text = uiState.pokemon.types[it].type,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PokemonDetailHeader(
    uiState: PokemonDetailsUIState,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = "#${uiState.pokemon.id}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = psFontFamily,
            color = uiState.primaryColorTheme.toArgb().getContrastingColor(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.Transparent)
                .padding(16.dp)
                .height(24.dp)
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = if (uiState.pokemon.isShinny) {
                    uiState.pokemon.officialArtworkModel.frontShiny
                } else {
                    uiState.pokemon.officialArtworkModel.frontDefault
                },
                contentDescription = null,
            )
            Text(
                text = uiState.pokemon.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = psFontFamily,
                color = uiState.primaryColorTheme.toArgb().getContrastingColor(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(vertical = 16.dp)
                    .height(24.dp)
                    .fillMaxWidth()
            )
        }
    }
}

fun gradient(
    isShinny: Boolean,
    colors: List<Color>
): Brush {
    return Brush.verticalGradient(
        colors = if (isShinny) colors.reversed() else colors
    )
}