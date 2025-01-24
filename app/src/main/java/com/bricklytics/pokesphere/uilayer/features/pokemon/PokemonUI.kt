package com.bricklytics.pokesphere.uilayer.features.pokemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.BottomSheet
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.ButtonOrientation
import com.bricklytics.pokesphere.uilayer.features.pokemon.model.PokemonEvent

@Composable
fun PokemonUI(navController: NavController, viewModel: PokemonViewModel) {

    viewModel.load()

    Scaffold(
        topBar = {
            Row {
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .weight(1f),
                    onClick = {
                        navController.navigate(AppRoutes.Home.route)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(50)
                            )
                            .size(16.dp)
                    )
                }
                Text(
                    text = viewModel.uiState.pokemonName,
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                )
            }
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(it)
            ) {
                viewModel.bottomSheetUiState.let { bottomSheet ->
                    BottomSheet (
                        visible = bottomSheet.enabled,
                        model = bottomSheet.model,
                        buttonOrientaion = ButtonOrientation.Vertical,
                        primaryAction = "OK" to { viewModel.onEvent(PokemonEvent.OnDismissBottomSheet) },
                        onDismiss = { viewModel.onEvent(PokemonEvent.OnDismissBottomSheet) }
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = "Home Screen"
                )

                viewModel.uiState.pokemon.abilities.forEach { abilities ->
                    Text(
                        text = abilities.ability.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        },
    )
}