package com.ggsb.restaurantenavapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ggsb.restaurantenavapp.navigation.AppDestination
import com.ggsb.restaurantenavapp.navigation.bottomAppBarItems
import com.ggsb.restaurantenavapp.sampledata.sampleProducts
import com.ggsb.restaurantenavapp.ui.screens.CheckoutScreen
import com.ggsb.restaurantenavapp.ui.screens.DrinksListScreen
import com.ggsb.restaurantenavapp.ui.screens.HighlightsListScreen
import com.ggsb.restaurantenavapp.ui.screens.MenuListScreen
import com.ggsb.restaurantenavapp.ui.screens.ProductDetailsScreen
import com.ggsb.restaurantenavapp.ui.theme.RestauranteNavAppTheme
import com.ggsb.restaurantenavapp.ui.theme.components.BottomAppBarItem
import com.ggsb.restaurantenavapp.ui.theme.components.PanucciBottomAppBar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry.value?.destination?.route
            /*navController.addOnDestinationChangedListener{
                _,_,_ ->
                val current = currentBackStackEntry.value?.destination?.route.map {
                    it.destination.route
                }
                Log.i()
            }*/
            //pegar a backstack
            val backStackEntryState by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntryState?.destination


            RestauranteNavAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedItem by remember(currentDestination) {
                        val item = currentDestination?.let { destination ->
                            bottomAppBarItems.find {
                                it.destination.route == destination.route
                            }
                        } ?: bottomAppBarItems.first()

                        mutableStateOf(item)
                    }
                    val containsBottomAppBarItems = currentDestination?.let { destination ->
                        bottomAppBarItems.find {
                            it.destination.route == destination.route
                        }
                    } != null
                    val isShowFab = when (currentDestination?.route) {
                        AppDestination.Menu.route,
                        AppDestination.Drinks.route -> true

                        else -> false
                    }
                    PanucciApp(
                        bottomAppBarItemSelected = selectedItem,
                        onBottomAppBarItemSelectedChange = {

                            val route = it.destination.route
                            navController.navigate(route) {
                                launchSingleTop = true
                                popUpTo(route)

                                restoreState = true
                            }

                        },
                        onFabClick = {
                            navController.navigate(AppDestination.Checkout.route)
                        },
                        isShowTopBar = containsBottomAppBarItems,
                        isShowBottomBar = containsBottomAppBarItems,
                        isShowFab = isShowFab,
                    ) {
                        // TODO implementar o nav host
                        NavHost(
                            navController = navController,
                            startDestination = AppDestination.HighLight.route
                        ) {
                            composable(AppDestination.HighLight.route) {
                                HighlightsListScreen(
                                    products = sampleProducts,
                                    onNavigateToDetails = { product ->
                                        navController.navigate("${AppDestination.ProductDetails.route}/${product.id}")
                                    },
                                    onNavigateToCheckout = {
                                        navController.navigate(AppDestination.Checkout.route)
                                    }
                                )
//                                    LaunchedEffect(Unit) {
//                                        delay(3000)
//                                        navController.navigate("menu")
//                                    }

                            }
                            composable(AppDestination.Menu.route) {
                                MenuListScreen(
                                    products = sampleProducts,
                                    onNavigateToDetails = { product ->
                                        navController.navigate("${AppDestination.ProductDetails.route}/${product.id}")
                                    }
                                )
                            }
                            composable(AppDestination.Drinks.route) {
                                DrinksListScreen(
                                    products = sampleProducts,
                                    onNavigateToDetails = { product ->
                                        navController.navigate("${AppDestination.ProductDetails.route}/${product.id}")
                                    }
                                )
                            }
                            composable("${AppDestination.ProductDetails.route}/{productId}") { backStackEntry ->
                                val id = backStackEntry.arguments?.getString("productId")
                                sampleProducts.find { it ->
                                    it.id == id
                                }?.let { product ->
                                    ProductDetailsScreen(
                                        product = product,
                                        onNavigateToCheckout = {
                                            navController.navigate(AppDestination.Checkout.route)
                                        }
                                    )
                                } ?: LaunchedEffect(Unit) {
                                    navController.navigateUp()
                                }

                            }
                            composable(AppDestination.Checkout.route) {
                                CheckoutScreen(
                                    products = sampleProducts,
                                    onPopBackStack = {
                                        navController.navigateUp()
                                    })
                            }
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanucciApp(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    onFabClick: () -> Unit = {},
    isShowTopBar: Boolean = false,
    isShowBottomBar: Boolean = false,
    isShowFab: Boolean = false,
    content: @Composable () -> Unit,

    ) {
    Scaffold(

        topBar = {
            if (isShowTopBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Ristorante Panucci")
                    },
                )
            }

        },
        bottomBar = {
            if (isShowBottomBar) {
                PanucciBottomAppBar(
                    item = bottomAppBarItemSelected,
                    items = bottomAppBarItems,
                    onItemChange = onBottomAppBarItemSelectedChange,
                )
            }

        },
        floatingActionButton = {
            if (isShowFab) {
                FloatingActionButton(
                    onClick = onFabClick
                ) {
                    Icon(
                        Icons.Filled.PointOfSale,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PanucciAppPreview() {
    RestauranteNavAppTheme {
        Surface {
            PanucciApp {}
        }
    }
}