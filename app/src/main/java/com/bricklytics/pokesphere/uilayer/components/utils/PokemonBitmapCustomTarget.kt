package com.bricklytics.pokesphere.uilayer.components.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class PokemonBitmapCustomTarget(
    val onColorReady : (Color) -> Unit
): CustomTarget<Bitmap>() {
    override fun onResourceReady(
        resource: Bitmap,
        transition: Transition<in Bitmap>?
    ) {
        resource.getDominantColor { color ->
            onColorReady(color)
        }
    }

    override fun onLoadCleared(placeholder: Drawable?) {

    }
}