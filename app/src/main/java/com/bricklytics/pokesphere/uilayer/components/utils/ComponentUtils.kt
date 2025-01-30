package com.bricklytics.pokesphere.uilayer.components.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import android.graphics.Color as AndroidColor

fun Int.getContrastingColor(): Color {
    val red = AndroidColor.red(this)
    val green = AndroidColor.green(this)
    val blue = AndroidColor.blue(this)
    val luminance = (0.299 * red + 0.587 * green + 0.114 * blue) / 255

    return if (luminance > 0.5) Color.Black else Color.White
}

fun Bitmap.getDominantColor( callback: (Color) -> Unit) {
    Palette.from(this).generate { palette ->
        val dominantColor = palette?.dominantSwatch?.rgb ?: 0
        callback(Color(dominantColor))
    }
}