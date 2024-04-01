package com.srnyndrs.android.imagefinder.feature_master.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.srnyndrs.android.imagefinder.feature_master.domain.model.ImageResult
import com.srnyndrs.android.imagefinder.core.domain.model.Screens
import com.srnyndrs.android.imagefinder.feature_master.presentation.components.RemoteImage
import com.srnyndrs.android.imagefinder.feature_master.presentation.components.SearchBar
import com.srnyndrs.android.imagefinder.ui.theme.ImageFinderTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun MasterScreen(
    navController: NavController,
    results: LazyPagingItems<ImageResult>,
    queryValue: StateFlow<String>,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberLazyGridState()
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            queryValue = queryValue,
            onSearch = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(index = 0)
                    onSearch(it)
                }
            },
            onQueryChange = onQueryChange
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { focusManager.clearFocus() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(results.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator()
                }

                is LoadState.Error -> {
                    val errorState = results.loadState.refresh as LoadState.Error
                    Text(
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.error,
                        text = errorState.error.message.toString().split(".").first()
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        state = scrollState,
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(results.itemCount) { index ->
                            val image = results[index]
                            if(image != null) {
                                RemoteImage(
                                    modifier = Modifier
                                        .requiredHeight(256.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .clickable {
                                            navController.navigate(Screens.Detail.route + "/${image.id.toLong()}")
                                        },
                                    imageUrl = image.imageUrl,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        if(results.loadState.append is LoadState.Loading) {
                            item(span = { GridItemSpan(2)} ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    LinearProgressIndicator()
                                }
                            }
                        } else if(results.loadState.append is LoadState.Error) {
                            item(span = { GridItemSpan(2)} ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(vertical = 6.dp),
                                        text = "Failed to load more results",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun MasterScreenPreview() {
    ImageFinderTheme {
        Surface {
            MasterScreen(
                navController = rememberNavController(),
                results = flowOf(
                    PagingData.from(
                        data = listOf(
                            ImageResult(
                                id = "",
                                imageUrl = ""
                            ),
                            ImageResult(
                                id = "",
                                imageUrl = ""
                            )
                        ),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                        )
                    )
                ).collectAsLazyPagingItems(),
                queryValue = MutableStateFlow("cat"),
                onSearch = {},
                onQueryChange = {}
            )
        }
    }
}