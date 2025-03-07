package com.bricklytics.pokesphere.uilayer.features.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCard
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeBottomBar
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.model.BottomBarItem
import com.bricklytics.pokesphere.uilayer.features.home.BottomBarMenuButtons.Companion.isKindOf
import com.bricklytics.pokesphere.uilayer.features.home.model.HomeEvents
import com.bricklytics.pokesphere.uilayer.features.home.model.HomeUIState
import com.guru.fontawesomecomposelib.FaIcons

@Preview(showSystemUi = true, device = Devices.PIXEL_6)
@Composable
private fun HomePreview() {
    MaterialTheme {
        HomeUIContent(
            onEvent = {},
            uiState = HomeUIState(
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
                colorTheme = Color.Magenta
            ),
        )
    }
}

@Composable
fun HomeUI(
    viewModel: HomeViewModel
) {
    HomeUIContent(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent
    )

    BackHandler {
        viewModel.onEvent(HomeEvents.OnBackPressed)
    }
}

@Composable
fun HomeUIContent(
    uiState: HomeUIState,
    onEvent: (HomeEvents) -> Unit,
) {
    Scaffold(
        topBar = {
            PokeTopBar(
                title = stringResource(R.string.home_title),
                color = uiState.colorTheme
            )
        },
        content = { paddingValues ->
            HomeContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        },
        bottomBar = {
            PokeBottomBar(
                items = uiState.bottomMenuItems,
                color = uiState.colorTheme,
                onClickItem = { index ->
                    when (index.isKindOf()) {
                        BottomBarMenuButtons.Home -> { onEvent(HomeEvents.OnHomePressed) }
                        BottomBarMenuButtons.Search -> { onEvent(HomeEvents.OnSearchPokemon) }
                        else -> Unit
                    }
                }
            )
        }
    )
}

@Composable
private fun HomeContent(
    paddingValues: PaddingValues,
    uiState: HomeUIState,
    onEvent: (HomeEvents) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(uiState.isLoading, uiState.favImage) {
        if (uiState.favImage.isNotBlank() && !uiState.isLoading) {
            onEvent(HomeEvents.OnFetchPokemonImage(context))
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (uiState.favImage.isNotBlank()) {
                PokeCard(
                    primaryImgUrl = uiState.favImage,
                    label = uiState.pokemonName,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

enum class BottomBarMenuButtons(
    val index: Int,
) {
    Home(0),
    Search(1),
    Undefined(-1);

    companion object {
        fun Int.isKindOf(): BottomBarMenuButtons {
            return entries.firstOrNull{ it.index == this } ?: Undefined
        }
    }
}