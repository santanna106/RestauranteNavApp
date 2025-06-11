package com.ggsb.restaurantenavapp.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ggsb.restaurantenavapp.model.Product
import com.ggsb.restaurantenavapp.sampledata.sampleProductWithImage
import com.ggsb.restaurantenavapp.sampledata.sampleProductWithoutImage
import com.ggsb.restaurantenavapp.ui.theme.RestauranteNavAppTheme

@Composable
fun DrinkProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .width(158.dp)
            .heightIn(
                min = 100.dp
            ),
        shape = RoundedCornerShape(12.dp)
    ) {
        product.image?.let { image ->
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = product.price.toPlainString(), maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun DrinkProductCardPreview() {
    RestauranteNavAppTheme {
        DrinkProductCard(
            product = sampleProductWithoutImage
        )
    }
}

@Preview
@Composable
private fun DrinkProductCardWithImagePreview() {
    RestauranteNavAppTheme {
        DrinkProductCard(
            product = sampleProductWithImage
        )
    }
}