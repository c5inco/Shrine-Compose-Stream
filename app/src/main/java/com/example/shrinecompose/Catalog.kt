package com.example.shrinecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@Composable
private fun CatalogCard(
    modifier: Modifier = Modifier,
    data: ItemData
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = data.photoResId),
                contentDescription = "Image description of photo",
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 220.dp)
            )
            Icon(
                imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = "Add to shopping cart",
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(id = getVendorResId(data.vendor)),
                contentDescription = "Vendor logo",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 12.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(data.title, style = MaterialTheme.typography.subtitle2)
        Spacer(Modifier.height(8.dp))
        Text("\$${data.price}", style = MaterialTheme.typography.body2)
        Spacer(Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun CatalogCardPreview() {
    ShrineComposeTheme {
        CatalogCard(data = SampleItems[0])
    }
}

@Composable
fun Catalog(
    items: List<ItemData>
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(end = 32.dp)
    ) {
        itemsIndexed(transformToWeavedList(items)) { idx, item ->
            val even = idx % 2 == 0
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .width((screenWidth * 0.66f).dp),
                horizontalAlignment = if (!even) Alignment.CenterHorizontally else Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                if (even) {
                    if (item.getOrNull(1) != null) {
                        CatalogCard(
                            modifier = Modifier
                                .align(Alignment.End)
                                .fillMaxWidth(0.85f), data = item[1]
                        )
                        Spacer(Modifier.height(40.dp))
                    }
                    CatalogCard(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        data = item[0]
                    )
                } else {
                    CatalogCard(
                        modifier = Modifier
                            .padding(top = 240.dp)
                            .fillMaxWidth(0.8f),
                        data = item[0]
                    )
                }
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

@Preview
@Composable
fun CatalogPreview() {
    ShrineComposeTheme {
        Surface {
            Box(
                Modifier.fillMaxSize()
            ) {
                Catalog(SampleItems.filter { it.category == Category.Clothing })
            }
        }
    }
}