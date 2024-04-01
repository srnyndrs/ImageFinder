package com.srnyndrs.android.imagefinder.feature_detail.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun ZoomableRemoteImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    backgroundColor: Color = Color.Transparent,
    imageAlign: Alignment = Alignment.Center,
    shape: Shape = RectangleShape,
    maxScale: Float = 1f,
    minScale: Float = 3f,
    contentScale: ContentScale = ContentScale.Fit,
    isRotation: Boolean = false,
    isZoomable: Boolean = true,
    scrollState: ScrollableState? = null
) {

    val coroutineScope = rememberCoroutineScope()

    val scale = remember { mutableFloatStateOf(1f) }
    val rotationState = remember { mutableFloatStateOf(1f) }
    val offsetX = remember { mutableFloatStateOf(1f) }
    val offsetY = remember { mutableFloatStateOf(1f) }

    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when(painter.state) {
            is AsyncImagePainter.State.Success -> {
                Box(
                    modifier = Modifier
                        .clip(shape)
                        .background(backgroundColor)
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { },
                            onDoubleClick = {
                                if (scale.floatValue >= 2f) {
                                    scale.floatValue = 1f
                                    offsetX.floatValue = 1f
                                    offsetY.floatValue = 1f
                                } else scale.floatValue = 3f
                            },
                        )
                        .pointerInput(Unit) {
                            if (isZoomable) {
                                awaitEachGesture {
                                    awaitFirstDown()
                                    do {
                                        val event = awaitPointerEvent()
                                        scale.floatValue *= event.calculateZoom()
                                        if (scale.floatValue > 1) {
                                            scrollState?.run {
                                                coroutineScope.launch {
                                                    setScrolling(false)
                                                }
                                            }
                                            val offset = event.calculatePan()
                                            offsetX.floatValue += offset.x
                                            offsetY.floatValue += offset.y
                                            rotationState.floatValue += event.calculateRotation()
                                            scrollState?.run {
                                                coroutineScope.launch {
                                                    setScrolling(true)
                                                }
                                            }
                                        } else {
                                            scale.floatValue = 1f
                                            offsetX.floatValue = 1f
                                            offsetY.floatValue = 1f
                                        }
                                    } while (event.changes.any { it.pressed })
                                }
                            }
                        }

                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = contentScale,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(imageAlign)
                            .graphicsLayer {
                                if (isZoomable) {
                                    scaleX = maxOf(maxScale, minOf(minScale, scale.floatValue))
                                    scaleY = maxOf(maxScale, minOf(minScale, scale.floatValue))
                                    if (isRotation) {
                                        rotationZ = rotationState.floatValue
                                    }
                                    translationX = offsetX.floatValue
                                    translationY = offsetY.floatValue
                                }
                            }
                    )
                }
            }
            is AsyncImagePainter.State.Error -> {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Warning Icon"
                )
            }
            else -> {
                CircularProgressIndicator()
            }
        }
    }
}

suspend fun ScrollableState.setScrolling(value: Boolean) {
    scroll(scrollPriority = MutatePriority.PreventUserInput) {
        when (value) {
            true -> Unit
            else -> awaitCancellation()
        }
    }
}