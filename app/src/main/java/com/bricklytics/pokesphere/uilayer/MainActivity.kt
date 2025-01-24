package com.bricklytics.pokesphere.uilayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.bricklytics.pokesphere.uilayer.base.navigation.AppNavGraph
import com.bricklytics.pokesphere.uilayer.components.ui.theme.PokesphereappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokesphereappTheme {
                val navController = rememberNavController()
                AppNavGraph.NavigationGraph(navController)
            }
        }
    }
}