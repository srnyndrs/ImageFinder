package com.srnyndrs.android.imagefinder.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.srnyndrs.android.imagefinder.core.domain.model.Screens
import com.srnyndrs.android.imagefinder.feature_detail.presentation.DetailViewModel
import com.srnyndrs.android.imagefinder.feature_detail.presentation.DetailScreen
import com.srnyndrs.android.imagefinder.feature_master.presentation.MasterViewModel
import com.srnyndrs.android.imagefinder.feature_master.presentation.MasterScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    masterViewModel: MasterViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Master.route
    ) {
        composable(
            route = Screens.Master.route
        ) {
            val results by rememberUpdatedState(newValue = masterViewModel.pagingState.collectAsLazyPagingItems())
            MasterScreen(
                navController = navHostController,
                results = results,
                queryValue = masterViewModel.searchQueryState,
                onSearch = masterViewModel::searchImages,
                onQueryChange = masterViewModel::setSearchQuery
            )
        }
        composable(
            route = Screens.Detail.route + "/{photoId}",
            arguments = listOf(
                navArgument("photoId") { type = NavType.LongType }
            )
        ) {backStackEntry ->
            val detailViewModel = hiltViewModel<DetailViewModel>()
            val photoId = backStackEntry.arguments?.getLong("photoId")
            val state by rememberUpdatedState(newValue = detailViewModel.detailState.collectAsState())
            val searchValue by masterViewModel.searchQueryState.collectAsStateWithLifecycle()
            DetailScreen(
                searchValue = searchValue,
                state = state,
                init = {
                    detailViewModel.getImage(photoId)
                },
                onBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}