package com.bricklytics.pokesphere.uilayer.features.pokemondetails

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.PokemonStatsType
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.colorPokemonStatsResource
import com.bricklytics.pokesphere.uilayer.features.pokemondetails.model.colorPokemonTypeResource
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
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                PokemonRowTypes(
                    uiState = uiState,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                PokemonStats(
                    uiState = uiState,
                )
            }
        }

        PokemonMoveList(
            uiState = uiState,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
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

@Composable
private fun PokemonRowTypes(
    uiState: PokemonDetailsUIState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        uiState.pokemon.types.forEach { item ->
            val colorType = colorResource(item.type.colorPokemonTypeResource())
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = colorType,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .requiredHeight(32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(2.dp)
                        .border(
                            border = BorderStroke(width = 2.dp, color = Color.White),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = item.type,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = psFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = colorType.toArgb().getContrastingColor()
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(2.dp)
                            .width(64.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonStats(
    uiState: PokemonDetailsUIState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = uiState.pokemon.weight.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = psFontFamily,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(R.string.pokemon_details_weight_label),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = psFontFamily
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = uiState.pokemon.height.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = psFontFamily,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(R.string.pokemon_details_height_label),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = psFontFamily
                )
            }
        }

        BaseStatus(
            stat = stringResource(R.string.pokemon_details_stats_abbrev_xp),
            baseStat = uiState.pokemon.baseExperience
        )
        uiState.pokemon.stats.forEach { item ->
            BaseStatus(
                stat = item.stat,
                baseStat = item.baseStat
            )
        }
    }
}

@Composable
private fun BaseStatus(
    stat: String,
    baseStat: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .requiredHeight(24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(stat.lowercase().convertToResource()),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = psFontFamily,
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f),
        )
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.light_deep),
                    shape = RoundedCornerShape(16.dp)
                )
                .weight(3f)
                .requiredHeight(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(stat.lowercase().colorPokemonStatsResource()),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxHeight()
                    .width(baseStat.dp)
            )
        }
    }
}

private fun String.convertToResource(): Int {
    return when (PokemonStatsType.fromLabel(this)) {
        PokemonStatsType.Xp -> R.string.pokemon_details_stats_abbrev_xp
        PokemonStatsType.Hp -> R.string.pokemon_details_stats_abbrev_hp
        PokemonStatsType.Attack -> R.string.pokemon_details_stats_abbrev_attack
        PokemonStatsType.Defense -> R.string.pokemon_details_stats_abbrev_defense
        PokemonStatsType.SpecialAttack -> R.string.pokemon_details_stats_abbrev_special_attack
        PokemonStatsType.SpecialDefense -> R.string.pokemon_details_stats_abbrev_special_defense
        PokemonStatsType.Speed -> R.string.pokemon_details_stats_abbrev_speed
    }
}

@Composable
fun PokemonMoveList(
    modifier: Modifier = Modifier,
    uiState: PokemonDetailsUIState,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isExpanded)
    val listHeight by transition.animateDp(
        transitionSpec = { tween(durationMillis = 500) },
        targetValueByState = { if (it) 300.dp else 40.dp }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(colorResource(R.color.light_down))
            .height(listHeight)
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    isExpanded = dragAmount < 0 // Expand when swiped up
                }
            }
    ) {
        Text(
            text = stringResource(R.string.pokemon_details_moves),
            fontSize = 16.sp,
            fontFamily = psFontFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { isExpanded = !isExpanded }
                )
        )

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                uiState.pokemon.moves.forEach { move ->
                    item {
                        Text(
                            text = move.moveName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = psFontFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}