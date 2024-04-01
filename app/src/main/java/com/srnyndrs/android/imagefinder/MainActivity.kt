package com.srnyndrs.android.imagefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.srnyndrs.android.imagefinder.feature_master.presentation.MasterViewModel
import com.srnyndrs.android.imagefinder.core.presentation.NavigationGraph
import com.srnyndrs.android.imagefinder.ui.theme.ImageFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHost = rememberNavController()
            val masterViewModel = hiltViewModel<MasterViewModel>()
            ImageFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationGraph(
                        navHostController = navHost,
                        masterViewModel = masterViewModel
                    )
                }
            }
        }
    }
}