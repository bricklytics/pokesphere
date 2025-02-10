package com.bricklytics.pokesphere.uilayer.components.features.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bricklytics.pokesphere.uilayer.R
import com.bricklytics.pokesphere.uilayer.components.features.utils.ShimmeringContainer
import com.bricklytics.pokesphere.uilayer.components.fonts.psFontFamily
import com.bricklytics.pokesphere.uilayer.components.utils.PokemonBitmapCustomTarget
import com.bricklytics.pokesphere.uilayer.components.utils.getContrastingColor
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Preview
@Composable
private fun PokeCardPreview() {
    MaterialTheme {
        PokeCard(
            primaryImgUrl = "",
            label = "Pikachu",
            onClick = {}
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokeCard(
    modifier: Modifier = Modifier,
    primaryImgUrl: String,
    secondaryImgUrl: String? = null,
    label: String,
    isFavorite: Boolean = false,
    isFlipped: Boolean = false,
    onClick: (() -> Unit)? = null,
    onDoubleTap: ((Boolean) -> Unit)? = null,
    onLongPress: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var likeIt by remember(isFavorite) { mutableStateOf(isFavorite) }
    var themeColor by remember { mutableStateOf(Color.Transparent) }

    LaunchedEffect(primaryImgUrl) {
        Glide.with(context)
            .asBitmap()
            .load(primaryImgUrl)
            .into(PokemonBitmapCustomTarget { color ->
                themeColor = color
            })
    }
    var isFront by remember(isFlipped) { mutableStateOf(!isFlipped) }
    val rotation by animateFloatAsState(
        targetValue = if (isFront) 0f else 360f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color.White),
        modifier = modifier
            .background(
                color = themeColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick?.run { invoke() }
                    },
                    onDoubleTap = {
                        onDoubleTap?.run { invoke(isFront) }
                        isFront = !isFront
                    },
                    onLongPress = {
                        likeIt = !likeIt
                        onLongPress?.run { invoke() }
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier.graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
        ) {
            FaIcon(
                faIcon = FaIcons.Star,
                tint = if (likeIt || isFavorite) colorResource(R.color.warning_down)
                else Color.Transparent,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
            )
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                GlideImage(
                    model = if (rotation < 90f) primaryImgUrl else secondaryImgUrl ?: primaryImgUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                )
                if (label.isNotBlank()) {
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = psFontFamily,
                        color = themeColor.toArgb().getContrastingColor(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(themeColor)
                            .padding(4.dp)
                            .height(24.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun PokeCardSkeleton() {
    ShimmeringContainer { brush ->
        Spacer(
            modifier = Modifier
                .padding(8.dp)
                .width(150.dp)
                .height(250.dp)
                .background(brush = brush, shape = RoundedCornerShape(8.dp))
        )
    }
}