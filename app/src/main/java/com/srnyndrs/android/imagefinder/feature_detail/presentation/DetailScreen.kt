package com.srnyndrs.android.imagefinder.feature_detail.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.srnyndrs.android.imagefinder.feature_detail.domain.model.ImageDetail
import com.srnyndrs.android.imagefinder.feature_detail.domain.model.DetailState
import com.srnyndrs.android.imagefinder.feature_detail.presentation.components.ZoomableRemoteImage
import com.srnyndrs.android.imagefinder.ui.theme.ImageFinderTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    searchValue: String,
    state: State<DetailState>,
    init: () -> Unit,
    onBack: () -> Unit
) {

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        init()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back button",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                text = "Results for \"${searchValue}\"",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if(state.value.loading == true) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else if(state.value.failure != null) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.value.failure!!)
                }
            } else {
                val image = state.value.success!!
                ZoomableRemoteImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(256.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    imageUrl = image.imageUrl,
                    isRotation = false,
                    contentScale = ContentScale.Fit,
                    scrollState = scrollState
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider(
                        modifier = Modifier
                            .padding(bottom = 12.dp, top = 6.dp)
                            .clip(RoundedCornerShape(32.dp)),
                        thickness = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = image.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun DetailScreenPreview() {
    ImageFinderTheme {
        Surface {
            DetailScreen(
                searchValue = "dog",
                state = MutableStateFlow(
                    DetailState(
                    success = ImageDetail(
                        imageUrl = "",
                        description = "asd"
                    )
                )
                ).collectAsState(),
                init = {},
                onBack = {}
            )
        }
    }
}