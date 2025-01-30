package com.bricklytics.pokesphere.uilayer.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCard
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeBottomBar
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.features.home.model.HomeEvents
import com.bricklytics.pokesphere.uilayer.features.home.model.HomeUIState

@Preview(showSystemUi = true, device = Devices.PIXEL_6)
@Composable
private fun HomePreview() {
    MaterialTheme {
        HomeUI(
            navController = rememberNavController(),
            viewModel = hiltViewModel<HomeViewModel>()
        )
    }
}

@Composable
fun HomeUI(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Scaffold(
        topBar = {
            PokeTopBar(
                title = stringResource(R.string.home_title),
                color = viewModel.uiState.colorTheme
            )
        },
        content = { paddingValues ->
            HomeContent(
                paddingValues = paddingValues,
                uiState = viewModel.uiState
            )
        },
        bottomBar = {
            PokeBottomBar(
                items = viewModel.uiState.bottomMenuItems,
                color =  viewModel.uiState.colorTheme,
                onClickItem = { index ->
                    when(index) {
                        0 -> { navController.navigate(AppRoutes.Home.route) }
                        1 -> { navController.navigate(AppRoutes.Pokemon.route) }
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
    onEvent: (HomeEvents) -> Unit = {}
) {
    val context  = LocalContext.current

    LaunchedEffect(uiState.favImage) {
        if(uiState.favImage.isNotBlank()) {
            onEvent(HomeEvents.OnFetchPokemonImage(context))
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            PokeCard(
                imgUrl = uiState.favImage,
                label = uiState.pokemonName,
                onClick =  { },
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}