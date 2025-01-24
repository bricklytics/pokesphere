package com.bricklytics.pokesphere.uilayer.components.features.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bricklytics.pokesphere.uilayer.components.features.bottomsheet.model.BottomSheetModel

@Preview
@Composable
private fun BottomSheetPreview() {
    var isVisible by remember { mutableStateOf(true) }

    val onDismiss: () -> Unit = {
        isVisible = !isVisible
    }

    MaterialTheme {
        Column {
            BottomSheet(
                model = BottomSheetModel(
                    title = "Ooops!",
                    subTitle = "Looks like something went wrong!",
                    footer = "404",
                    message = "Wait a little and try again, rigth!?",
                ),
                visible = isVisible,
                buttonOrientaion = ButtonOrientation.Horizontal,
                onDismiss = { onDismiss() },
                primaryAction = "Dismiss" to { onDismiss() },
                secondaryAction = "Also dismiss" to { onDismiss() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    model: BottomSheetModel,
    visible: Boolean,
    buttonOrientaion: ButtonOrientation = ButtonOrientation.Vertical,
    onDismiss: (() -> Unit)? = null,
    primaryAction: Pair<String, () -> Unit>,
    secondaryAction: Pair<String, (() -> Unit)>? = null,
) {
    if (!visible) return

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        sheetState = bottomSheetState,
        shape = BottomSheetDefaults.ExpandedShape,
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false),
        dragHandle = null,
        contentWindowInsets = {
            WindowInsets(left = 4.dp, top = 0.dp, right = 4.dp, bottom = 0.dp)
        },
        onDismissRequest = { onDismiss?.let { it() } }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { onDismiss?.let { it() } }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = Color.Gray
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = model.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = model.subTitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = model.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = model.footer,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                when (buttonOrientaion) {
                    ButtonOrientation.Horizontal ->
                        QueuedBottomSheetButtons(primaryAction, secondaryAction)

                    ButtonOrientation.Vertical ->
                        StackedBottomSheetButtons(primaryAction, secondaryAction)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun QueuedBottomSheetButtons(
    primaryAction: Pair<String, () -> Unit>,
    secondaryAction: Pair<String, () -> Unit>?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            onClick = { primaryAction.second() }
        ) {
            Text(text = primaryAction.first)
        }

        if (secondaryAction != null) {
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f),
                onClick = { secondaryAction.second() }
            ) {
                Text(
                    text = secondaryAction.first,
                )
            }
        }
    }
}

@Composable
private fun StackedBottomSheetButtons(
    primaryAction: Pair<String, () -> Unit>,
    secondaryAction: Pair<String, () -> Unit>?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            onClick = { primaryAction.second() }
        ) {
            Text(text = primaryAction.first)
        }

        if (secondaryAction != null) {
            Button(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                onClick = { secondaryAction.second() }
            ) {
                Text(
                    text = secondaryAction.first,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


enum class ButtonOrientation {
    Horizontal,
    Vertical;
}