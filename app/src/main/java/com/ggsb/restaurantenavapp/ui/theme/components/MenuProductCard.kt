package com.ggsb.restaurantenavapp.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ggsb.restaurantenavapp.model.Product
import com.ggsb.restaurantenavapp.sampledata.sampleProductWithImage
import com.ggsb.restaurantenavapp.sampledata.sampleProductWithoutImage
import com.ggsb.restaurantenavapp.ui.theme.RestauranteNavAppTheme
import com.ggsb.restaurantenavapp.R

@Composable
fun MenuProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row {
            Column(
                Modifier
                    .padding(16.dp)
                    .weight(3f)
            ) {
                Text(
                    text = product.name,
                    Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = product.price.toPlainString(),
                    Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(400)
                )
            }
            product.image?.let { image ->
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    Modifier
                        .width(80.dp)
                        .fillMaxHeight(),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun MenuProductCardPreview() {
    RestauranteNavAppTheme {
        MenuProductCard(
            product = sampleProductWithoutImage
        )
    }
}

@Preview
@Composable
fun MenuProductCardWithImagePreview() {
    RestauranteNavAppTheme {
        MenuProductCard(
            product = sampleProductWithImage
        )
    }
}