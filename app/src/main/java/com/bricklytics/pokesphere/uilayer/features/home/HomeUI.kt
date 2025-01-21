package com.bricklytics.pokesphere.uilayer.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes


@Composable
fun HomeUI(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Scaffold(
        topBar = {
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.height(48.dp)
            ){
                Text(
                    text = "Home",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Home Screen")
            }
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClick = {
                    val route = AppRoutes.Pokemon.setArgument("bulbasaur")
                    navController.navigate(route)
                }
            ) {
                Text("Get Pokemon")
            }
        }
    )
}