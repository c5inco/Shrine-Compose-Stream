/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.shrinecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.SampleData.Companion.SampleItems
import com.example.shrinecompose.ui.theme.Theme.Companion.ShrineComposeTheme

@Composable
private fun CartHeader(cartSize: Int, onCollapse: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onCollapse() },
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
            CartHeader(cartSize = 15, onCollapse = {})
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
            CartItem(SampleItems[0])
        }
    }
}

@Composable
fun ExpandedCart(
    items: List<ItemData> = SampleItems,
    onCollapse: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colors.secondary
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CartHeader(
                cartSize = items.size,
                onCollapse = onCollapse
            )

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

@Preview(device = Devices.PIXEL_4)
@Composable
fun ExpandedCartPreview() {
    ShrineComposeTheme {
        ExpandedCart()
    }
}

@Composable
private fun CollapsedCart(
    items: List<ItemData> = SampleItems.subList(fromIndex = 0, toIndex = 2),
    onTap: () -> Unit = {}
) {
    Row(
        Modifier
            .clickable { onTap() }
            .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping cart icon",
            )
        }
        items.forEach { item ->
            CollapsedCartItem(data = item)
        }
    }
}

@Composable
private fun CollapsedCartItem(data: ItemData) {
    Image(
        painter = painterResource(id = data.photoResId),
        contentDescription = data.title,
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@Preview
@Composable
fun CollapsedCartPreview() {
    ShrineComposeTheme {
        Surface(
            color = MaterialTheme.colors.secondary
        ) {
            CollapsedCart()
        }
    }
}

@Composable
fun CartBottomSheet(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(56.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp
    ) {
        CollapsedCart()
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun CartBottomSheetPreview() {
    ShrineComposeTheme {
        Box(
            Modifier.fillMaxSize()
        ) {
            CartBottomSheet(Modifier.align(Alignment.BottomEnd))
        }
    }
}