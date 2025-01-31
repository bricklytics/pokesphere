package com.bricklytics.pokesphere.uilayer.components.features.navigationbar

import android.os.Build
import android.view.View
import android.view.Window
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import com.bricklytics.pokesphere.uilayer.components.features.navigationbar.model.BottomBarItem
import com.bricklytics.pokesphere.uilayer.components.fonts.psFontFamily
import com.bricklytics.pokesphere.uilayer.components.fonts.psTypography
import com.bricklytics.pokesphere.uilayer.components.utils.getContrastingColor
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_6_PRO
)
@Composable
private fun NavigationBarPreview() {
    val items = listOf(
        BottomBarItem(
            resIcon = FaIcons.Home,
            label = "Home"
        ),
        BottomBarItem(
            resIcon = FaIcons.Search,
            label = "Search"
        ),
        BottomBarItem(
            resIcon = FaIcons.BookReader,
            label = "Wiki"
        ),
        BottomBarItem(
            resIcon = FaIcons.Gamepad,
            label = "Games"
        )
    )

    MaterialTheme {
        Scaffold(
            topBar = {
                PokeTopBar(
                    title = "Title",
                    color = Color.Yellow,
                    onClickIcon = { },
                    onClickToolbar = { },
                    onClickMenu = { }
                )
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    Text("Content")
                }
            },
            bottomBar = {
                PokeBottomBar(
                    items = items,
                    color = Color.Yellow,
                    onClickItem = {}
                )
            }
        )
    }
}

@Composable
fun PokeTopBar(
    title: String,
    color: Color = Color.Transparent,
    onClickIcon: (() -> Unit)? = null,
    onClickToolbar: (() -> Unit)? = null,
    onClickMenu: (() -> Unit)? = null
) {
    val view = LocalView.current
    val density = LocalDensity.current

    val contrastColor = color.toArgb().getContrastingColor()

    LocalActivity.current?.let {
        setSystemBarColors(view, it.window, color, contrastColor != Color.White)
    }

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
            if (onClickIcon != null) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = { onClickIcon() }
                ) {
                    FaIcon(
                        modifier = Modifier.size(24.dp),
                        faIcon = FaIcons.ArrowLeft,
                        tint = contrastColor
                    )
                }
            }

            if(onClickMenu != null ){
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { onClickMenu() }
                ) {
                    FaIcon(
                        modifier = Modifier.align(Alignment.Center),
                        faIcon = FaIcons.Bars,
                        size = 24.dp,
                        tint = contrastColor
                    )
                }
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                fontFamily = psFontFamily,
                style = psTypography.titleMedium,
                color = contrastColor
            )
        }
    }
}

@Composable
fun PokeBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomBarItem>,
    color: Color = Color.Transparent,
    onClickItem: (Int) -> Unit
) {
    val contrastColor = color.toArgb().getContrastingColor()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .requiredHeight(72.dp)
                .background(color = color)
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .requiredHeight(64.dp)
                        .padding(4.dp)
                ) {
                    IconButton(
                        modifier = Modifier
                            .requiredWidth(64.dp)
                            .fillMaxHeight(),
                        onClick = { onClickItem(index) }
                    ) {
                        FaIcon(
                            faIcon = item.resIcon,
                            tint = contrastColor,
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.TopCenter)
                        )
                        Text(
                            text = item.label,
                            fontFamily = psFontFamily,
                            style = psTypography.bodySmall,
                            color = contrastColor,
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
        if (hasEdgeToEdgeSupport()) {
            Spacer(
                modifier = Modifier
                    .height(getNavigationBarHeight())
                    .background(color = color)
                    .fillMaxWidth()
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

fun getStatusBarHeight(view: View, density: Density): Dp {
    val windowInsets = WindowInsetsCompat.toWindowInsetsCompat(
        view.rootWindowInsets
    )
    val statusBarHeightPx = windowInsets.getInsets(Type.statusBars()).top

    return with(density) { statusBarHeightPx.toDp() }
}

@Composable
fun getNavigationBarHeight(): Dp {
    val view = LocalView.current
    val density = LocalDensity.current

    val windowInsets = WindowInsetsCompat.toWindowInsetsCompat(
        view.rootWindowInsets
    )
    val navigationBarHeightPx = windowInsets.getInsets(Type.navigationBars()).bottom

    return with(density) { navigationBarHeightPx.toDp() }
}

@Suppress("DEPRECATION")
private fun setSystemBarColors(
    view: View,
    window: Window,
    color: Color,
    isDark: Boolean
) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        window.statusBarColor = color.toArgb()
        window.decorView.isForceDarkAllowed = !isDark
        window.navigationBarColor = color.toArgb()

        if (!view.isInEditMode) {
            window.let {
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
