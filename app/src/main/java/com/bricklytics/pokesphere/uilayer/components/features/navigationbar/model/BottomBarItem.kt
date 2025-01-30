package com.bricklytics.pokesphere.uilayer.components.features.navigationbar.model

import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons

data class BottomBarItem(
    val resIcon: FaIconType = FaIconType.SolidIcon(FaIcons.Home.src),
    val label: String,
)
