package com.bricklytics.pokesphere.uilayer.features.pokemon

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import com.bricklytics.pokesphere.domainlayer.features.pokemon.model.PokemonModel
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.BottomSheet
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.ButtonOrientation
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCard
import com.bricklytics.pokesphere.uilayer.components.features.card.PokeCardSkeleton
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.components.fonts.psFontFamily
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonEvent
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonUIState
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun PokemonUI(navController: NavController, viewModel: PokemonViewModel) {

    var colorTheme by remember { mutableStateOf(Color.White) }

    Scaffold(
        topBar = {
            PokeTopBar(
                title = stringResource(R.string.pokemon_ui_title),
                icon = null,
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
                    viewModel.uiState,
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
        snapshotFlow {
            lazyGridState.layoutInfo.run {
                visibleItemsInfo.lastOrNull()?.index == totalItemsCount - 1
            }
        }.collect { isScrolledToTheEnd ->
            if (isScrolledToTheEnd && !lazyGridState.canScrollForward && lazyGridState.isScrollInProgress) {
                onEvent()
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
    ) {
        if(uiState.isLoading) {
            items(6) {
                PokeCardSkeleton()
            }
        }

        items(uiState.pokemonList.size) { index ->
            PokeCard(
                label = uiState.pokemonList[index].name,
                imgUrl = uiState.pokemonList[index].officialArtworkModel.frontDefault,
                onClick = {}
            )
//
//            @OptIn(ExperimentalGlideComposeApi::class)
//            Card(
//                shape = RoundedCornerShape(8.dp),
//                border = BorderStroke(2.dp, Color.White),
//                modifier = Modifier
//                    .background(Color.LightGray)
//                    .padding(8.dp)
//            ) {
//                val list = uiState.pokemonList
//                GlideImage(
//                    model = list[index].officialArtworkModel.frontDefault,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .align(Alignment.CenterHorizontally),
//                )
//                Text(
//                    text = list[index].name,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Normal,
//                    fontFamily = psFontFamily,
//                    modifier = Modifier
//                        .padding(horizontal = 4.dp, vertical = 8.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//            }
        }
    }
}


@Composable
private fun getImage(
    pokemon: PokemonModel,
    context: Context
): Pair<Color, Bitmap> {
    val colorTheme = Color.Transparent
    var bitmap by remember {
        mutableStateOf(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    }

    LaunchedEffect(pokemon) {
        bitmap = Glide.with(context)
            .asBitmap()
            .load(pokemon.officialArtworkModel.frontDefault)
            .submit()
            .get()

        Palette.from(bitmap).generate().let {
            Color(it.getDominantColor(Color.White.toArgb()))
        }
    }
    return colorTheme to bitmap
}