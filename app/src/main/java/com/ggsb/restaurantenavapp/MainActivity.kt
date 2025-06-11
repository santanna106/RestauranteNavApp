package com.ggsb.restaurantenavapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ggsb.restaurantenavapp.sampledata.bottomAppBarItems
import com.ggsb.restaurantenavapp.sampledata.sampleProductWithImage
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
            Log.i("MainActivity", "onCreate: navController $navController")
            val initialScreen = "Destaques"
            val screens = remember {
                mutableStateListOf(initialScreen)
            }
            Log.i("MainActivity", "onCreate: screens ${screens.toList()}")
            val currentScreen = screens.last()
            BackHandler(screens.size > 1) {
                screens.removeLast()
            }
            RestauranteNavAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedItem by remember(currentScreen) {
                        val item = bottomAppBarItems.find { currentScreen == it.label }
                        mutableStateOf(item)
                    }
                    PanucciApp(
                        bottomAppBarItemSelected = selectedItem ?: bottomAppBarItems.first(),
                        onBottomAppBarItemSelectedChange = {
                            selectedItem = it
                            screens.add(it.label)
                        },
                        onFabClick = {
                            screens.add("Pedido")
                        }) {
                        when (currentScreen) {
                            "Destaques" -> HighlightsListScreen(
                                products = sampleProducts,
                                onOrderClick = {
                                    screens.add("Pedido")
                                },
                                onProductClick = {
                                    screens.add("DetalhesProduto")
                                }
                            )
                            "Menu" -> MenuListScreen(
                                products = sampleProducts
                            )
                            "Bebidas" -> DrinksListScreen(
                                products = sampleProducts + sampleProducts
                            )
                            "DetalhesProduto" -> ProductDetailsScreen(
                                product = sampleProductWithImage
                            )
                            "Pedido" -> CheckoutScreen(products = sampleProducts)
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
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Ristorante Panucci")
                },
            )
        },
        bottomBar = {
            PanucciBottomAppBar(
                item = bottomAppBarItemSelected,
                items = bottomAppBarItems,
                onItemChange = onBottomAppBarItemSelectedChange,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick
            ) {
                Icon(
                    Icons.Filled.PointOfSale,
                    contentDescription = null
                )
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