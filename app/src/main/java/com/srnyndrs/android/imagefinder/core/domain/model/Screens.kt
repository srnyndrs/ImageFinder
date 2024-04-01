package com.srnyndrs.android.imagefinder.core.domain.model

sealed class Screens(val route: String) {
    data object Master: Screens("master")
    data object Detail: Screens("detail")
}