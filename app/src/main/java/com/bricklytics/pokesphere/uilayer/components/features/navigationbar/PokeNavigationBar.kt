package com.bricklytics.pokesphere.uilayer.components.features.navigationbar

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import android.graphics.Color as AndroidColor


@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_6_PRO
)
@Composable
private fun NavigationBarPreview() {
    MaterialTheme {
        Scaffold(
            topBar = {
                PokeNavigationBar(
                    title = "Title",
                    icon = null,
                    onClickIcon = { },
                    onClickToolbar = { }
                )
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    Text("Content")
                }
            },
            bottomBar = { }
        )
    }
}

@Composable
fun PokeNavigationBar(
    title: String,
    icon: Icons? = null,
    color: Color = Color.Transparent,
    visibleIcon: Boolean = true,
    onClickIcon: (() -> Unit)? = null,
    onClickToolbar: (() -> Unit)? = null
) {
    val view = LocalView.current
    val density = LocalDensity.current

    val bestColor = getContrastingColor(color.toArgb())
    setStatusBarColors(view, color, bestColor != Color.White)

    Column(Modifier.background(color = color)) {
        if (hasEdgeToEdgeSupport()) {
            Spacer(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(getStatusBarHeight(view, density))
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .clickable(
                    enabled = onClickToolbar != null,
                    onClick = { onClickToolbar?.invoke() }
                )
                .fillMaxWidth()
                .size(48.dp)
        ) {
            if (visibleIcon) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = { onClickIcon?.invoke() }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = bestColor
                    )
                }
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                color = bestColor
            )
        }
    }
}

@Composable
fun hasEdgeToEdgeSupport(): Boolean {
    val paddingTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val paddingBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    return paddingBottom > 0.dp || paddingTop > 0.dp
}

private fun getStatusBarHeight(view: View, density: Density): Dp {
    val windowInsets = WindowInsetsCompat.toWindowInsetsCompat(
        view.rootWindowInsets
    )
    val statusBarHeightPx = windowInsets.getInsets(Type.statusBars()).top

    return with(density) { statusBarHeightPx.toDp() }
}

@Suppress("DEPRECATION")
private fun setStatusBarColors(view: View, color: Color, isDark: Boolean) {
    val window = (view.context as Activity).window

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        window.statusBarColor = color.toArgb()
        window.decorView.isForceDarkAllowed = !isDark

        if (!view.isInEditMode) {
            window?.let {
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = isDark
                view.systemUiVisibility = if (isDark) {
                    view.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    view.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
            }
        }
    }
}

private fun getContrastingColor(backgroundColor: Int): Color {
    val red = AndroidColor.red(backgroundColor)
    val green = AndroidColor.green(backgroundColor)
    val blue = AndroidColor.blue(backgroundColor)
    val luminance = (0.299 * red + 0.587 * green + 0.114 * blue) / 255

    return if (luminance > 0.5) Color.Black else Color.White
}
