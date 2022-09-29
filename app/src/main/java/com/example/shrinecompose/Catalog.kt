package com.example.shrinecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@Preview
@Composable
private fun CatalogCardPreview() {
    ShrineComposeTheme {
        CatalogCard(
            modifier = Modifier.height(380.dp),
            data = SampleItems[0]
        )
    }
}

@Composable
private fun CatalogCard(
    modifier: Modifier = Modifier,
    data: ItemData,
    onAdd: (ItemData) -> Unit = {}
) {
    Column(
        modifier = modifier.clickable { onAdd(data) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.weight(1f)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = data.photoResId),
                contentDescription = data.title,
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = "Add to cart"
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset { IntOffset(x = 0, y = 12.dp.roundToPx()) },
                painter = painterResource(id = getVendorResId(data.vendor)),
                contentDescription = "${data.vendor} logo"
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(
            data.title,
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            "$${data.price}",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun CatalogPreview() {
    ShrineComposeTheme {
        Surface {
            Box(
                Modifier.fillMaxSize()
            ) {
                Catalog(items = SampleItems)
            }
        }
    }
}

private fun <T> transformToWeavedList(items: List<T>): List<List<T>> {
    var i = 0
    val list = mutableListOf<List<T>>()
    while (i < items.size) {
        val even = i % 3 == 0
        val wList = mutableListOf<T>()
        wList.add(items[i])
        if (even && i + 1 < items.size) wList.add(items[i + 1])
        list.add(wList.toList())
        i += if (even) 2 else 1
    }
    return list.toList()
}

@Composable
fun Catalog(
    modifier: Modifier = Modifier,
    items: List<ItemData>,
    onAddItemToCart: (ItemData) -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    LazyRow(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(end = 32.dp)
    ) {
        itemsIndexed(
            items = transformToWeavedList(items),
            key = { _, item -> item[0].id }
        ) { idx, item ->
            val even = idx % 2 == 0

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(vertical = 48.dp, horizontal = 16.dp)
                    .width((screenWidth * 0.66f).dp),
                horizontalAlignment = if (even) Alignment.Start else Alignment.CenterHorizontally,
                verticalArrangement = if (even) Arrangement.Bottom else Arrangement.Center,
            ) {
                if (even) {
                    if (item.getOrNull(1) != null) {
                        CatalogCard(
                            modifier = Modifier
                                .align(Alignment.End)
                                .weight(1f)
                                .fillMaxWidth(0.85f),
                            data = item[1],
                            onAdd = onAddItemToCart
                        )
                        Spacer(Modifier.height(40.dp))
                        CatalogCard(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(0.85f),
                            data = item[0],
                            onAdd = onAddItemToCart
                        )
                    } else {
                        CatalogCard(
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth(0.85f),
                            data = item[0],
                            onAdd = onAddItemToCart
                        )
                    }
                } else {
                    CatalogCard(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth(0.8f),
                        data = item[0],
                        onAdd = onAddItemToCart
                    )
                }
            }
        }
    }
}