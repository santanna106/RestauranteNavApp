package com.ggsb.restaurantenavapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import com.ggsb.restaurantenavapp.ui.theme.components.BottomAppBarItem

sealed class AppDestination(val route:String) {
    object HighLight: AppDestination("highlight")
    object Menu: AppDestination("menu")
    object Drinks: AppDestination("drinks")
    object ProductDetails: AppDestination("productDetails")
    object Checkout: AppDestination("checkout")
}

val bottomAppBarItems = listOf(
    BottomAppBarItem(
        label = "Destaques",
        icon = Icons.Filled.AutoAwesome,
        destination = AppDestination.HighLight
    ),
    BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
        destination= AppDestination.Menu
    ),
    BottomAppBarItem(
        label = "Bebidas",
        icon = Icons.Outlined.LocalBar,
        destination=AppDestination.Drinks
    ),
)