package com.srnyndrs.android.imagefinder.feature_master.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun RemoteImage(
    modifier: Modifier,
    imageUrl: String,
    contentScale: ContentScale
) {

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
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = "Image result",
                    contentScale = contentScale
                )
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