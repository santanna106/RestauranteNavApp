package com.ggsb.restaurantenavapp.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ggsb.restaurantenavapp.R
import com.ggsb.restaurantenavapp.model.Product
import com.ggsb.restaurantenavapp.sampledata.sampleProductWithImage
import com.ggsb.restaurantenavapp.sampledata.sampleProductWithoutImage
import com.ggsb.restaurantenavapp.ui.theme.CheckoutCircleButton
import com.ggsb.restaurantenavapp.ui.theme.RestauranteNavAppTheme

@Composable
fun CheckoutItemCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .height(80.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                Modifier
                    .weight(9f)
                    .fillMaxHeight()
            ) {
                product.image?.let { image ->
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        Modifier.width(80.dp),
                        placeholder = painterResource(
                            id = R.drawable.placeholder
                        ),
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.price.toPlainString())
                }
            }
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var quantity by remember {
                    mutableStateOf(1)
                }
                val circleButtonModifier = Modifier
                    .size(20.dp)
                    .background(
                        CheckoutCircleButton,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                Box(
                    circleButtonModifier
                        .clickable { quantity++ }
                ) {
                    Icon(
                        Icons.Filled.ArrowDropUp,
                        contentDescription = null
                    )
                }
                Text(text = "$quantity")
                Box(
                    circleButtonModifier
                        .clickable {
                            if (quantity > 1) {
                                quantity--
                            }
                        }
                ) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CheckoutItemCardPreview() {
    RestauranteNavAppTheme {
        CheckoutItemCard(
            product = sampleProductWithoutImage
        )
    }
}

@Preview
@Composable
private fun CheckoutItemCardWithImagePreview() {
    RestauranteNavAppTheme {
        CheckoutItemCard(
            product = sampleProductWithImage
        )
    }
}