package com.bricklytics.pokesphere.uilayer.components.features.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bricklytics.pokesphere.uilayer.components.features.utils.ShimmeringContainer
import com.bricklytics.pokesphere.uilayer.components.fonts.psFontFamily
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Preview
@Composable
private fun PokeCardPreview() {
    MaterialTheme {
        PokeCard(
            imgUrl = "",
            label = "Pikachu",
            onClick = {}
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokeCard(
    modifier: Modifier = Modifier,
    imgUrl: String,
    label: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color.White),
        modifier = modifier
            .background(Color.LightGray)
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        GlideImage(
            model = imgUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = psFontFamily,
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
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
                .background(brush = brush, shape = RoundedCornerShape(8.dp),)
        )
    }
}