package com.bricklytics.pokesphere.uilayer.components.fonts

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bricklytics.pokesphere.uilayer.R

val psFontFamily = FontFamily(
    Font(R.font.kalam_regular,FontWeight.Normal),
    Font(R.font.kalam_bold, FontWeight.Bold),
    Font(R.font.kalam_light, FontWeight.Light)

)

val psTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = psFontFamily,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = psFontFamily,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = TextStyle(
        fontFamily = psFontFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold
    )
)