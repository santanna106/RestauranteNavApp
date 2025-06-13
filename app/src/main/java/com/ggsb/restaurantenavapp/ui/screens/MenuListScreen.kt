package com.ggsb.restaurantenavapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ggsb.restaurantenavapp.model.Product
import com.ggsb.restaurantenavapp.sampledata.sampleProducts
import com.ggsb.restaurantenavapp.ui.theme.RestauranteNavAppTheme
import com.ggsb.restaurantenavapp.ui.theme.caveatFont
import com.ggsb.restaurantenavapp.ui.theme.components.MenuProductCard

@Composable
fun MenuListScreen(
    modifier: Modifier = Modifier,
    title: String = "Menu",
    products: List<Product> = emptyList(),
    onNavigateToDetails: (Product) -> Unit
) {
    Column(
        modifier.fillMaxSize()
    ) {
        Surface {
            Text(
                text = title,
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                fontFamily = caveatFont,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(
            modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { p ->
                MenuProductCard(
                    product = p,
                    Modifier
                        .clickable{
                            onNavigateToDetails(p)
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun MenuListScreenPreview() {
    RestauranteNavAppTheme {
        Surface {
            MenuListScreen(
                products = sampleProducts,
                onNavigateToDetails = {}
            )
        }
    }
}