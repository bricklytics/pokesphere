package com.bricklytics.pokesphere.uilayer.components.features.utils

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun shimmeringBrush(): Brush {
    val gradient = listOf(
        Color(0xFFE0E2EE).copy(alpha = 0.9f),
        Color(0xFFF3F5FE).copy(alpha = 0.3f),
        Color(0xFFCED1E1).copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition
        .animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 800,
                    easing = FastOutLinearInEasing
                ),
            ),
            label = ""
        )

    return Brush.linearGradient(
        colors = gradient,
        start = Offset.Zero,
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
}


@Composable
fun ShimmeringContainer(
    content: @Composable (Brush) -> Unit
) {
    content(shimmeringBrush())
}