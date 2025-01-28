package com.bricklytics.pokesphere.uilayer.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.base.navigation.AppRoutes
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.PokeTopBar
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.getNavigationBarHeight
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.hasEdgeToEdgeSupport

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
                visibleIcon = false,
                color = Color.Yellow
            )
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
            Column {
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
                if (hasEdgeToEdgeSupport()) {
                    Spacer(
                        modifier = Modifier
                            .height(getNavigationBarHeight())
                            .fillMaxWidth()
                    )
                }
            }
        }
    )
}