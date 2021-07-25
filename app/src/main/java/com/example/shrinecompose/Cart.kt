package com.example.shrinecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@Composable
private fun CartHeader(cartSize: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
            Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Collapse cart icon"
            )
        }
        Text(
            "Cart".uppercase(),
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(Modifier.width(12.dp))
        Text("$cartSize items".uppercase())
    }
}

@Preview(name = "Cart header")
@Composable
fun CartHeaderPreview() {
    ShrineComposeTheme {
        Surface(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.secondary
        ) {
            CartHeader(cartSize = 15)
        }
    }
}

@Composable
private fun CartItem(
    item: ItemData
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
            Modifier.padding(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = "Remove item icon"
            )
        }
        Column(
            Modifier.fillMaxWidth()
        ) {
            Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
            Row(
                Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.photoResId),
                    contentDescription = "Image for: ${item.title}",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(Modifier.width(20.dp))
                Column(
                    Modifier.padding(end = 16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.vendor}".uppercase(),
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            text = "$${item.price}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        }
    }
}

@Preview(name = "Cart item")
@Composable
fun CartItemPreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.secondary) {
            CartItem(SampleItemsData[0])
        }
    }
}

@Composable
fun Cart(
    items: List<ItemData> = SampleItemsData
) {
    Surface(
        color = MaterialTheme.colors.secondary
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CartHeader(items.size)

            // Items
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEach { item ->
                    CartItem(item)
                }
            }

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddShoppingCart,
                    contentDescription = "Add to card icon"
                )
                Spacer(Modifier.width(16.dp))
                Text("Add to cart".uppercase())
            }
        }
    }
}

@Preview(name = "Full cart", device = Devices.PIXEL_4)
@Composable
fun CartPreview() {
    ShrineComposeTheme {
        Cart()
    }
}