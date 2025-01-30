package com.bricklytics.pokesphere.uilayer.features.home.model

import androidx.compose.ui.graphics.Color
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.model.BottomBarItem

data class HomeUIState(
    val isLoading: Boolean = false,
    val bottomMenuItems: List<BottomBarItem> = emptyList(),
    val colorTheme: Color = Color.Transparent,
    val favImage: String = "",
    val pokemonName: String = ""
)
