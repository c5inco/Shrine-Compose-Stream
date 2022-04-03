package com.example.shrinecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@Composable
private fun CatalogCard(
    modifier: Modifier = Modifier,
    data: ItemData,
    onAdd: (AddCartItemData) -> Unit
) {
    var size = IntSize.Zero
    var position = Offset.Zero

    Column(
        modifier = modifier
            .onGloballyPositioned {
                size = it.size
                position = it.positionInWindow()
            }
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onAdd(AddCartItemData(data, size, position))
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = data.photoResId),
                contentDescription = "Photo of ${data.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
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
        Text(
            "\$${data.price}",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun CatalogCardPreview() {
    ShrineComposeTheme {
        CatalogCard(
            modifier = Modifier.height(380.dp),
            data = SampleItems[0],
            onAdd = { println(it) }
        )
    }
}

@Composable
fun Catalog(
    modifier: Modifier = Modifier,
    items: List<ItemData>,
    onAddCartItem: (AddCartItemData) -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    LazyRow(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(end = 32.dp)
    ) {
        itemsIndexed(transformToWeavedList(items)) { idx, item ->
            val even = idx % 2 == 0
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(vertical = 48.dp, horizontal = 16.dp)
                    .width((screenWidth * 0.66f).dp),
                horizontalAlignment = if (!even) Alignment.CenterHorizontally else Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                if (even) {
                    if (item.getOrNull(1) != null) {
                        CatalogCard(
                            modifier = Modifier
                                .align(Alignment.End)
                                .weight(1f)
                                .fillMaxWidth(0.85f),
                            data = item[1],
                            onAdd = onAddCartItem
                        )
                        Spacer(Modifier.height(40.dp))
                        CatalogCard(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(0.85f),
                            data = item[0],
                            onAdd = onAddCartItem
                        )
                    } else {
                        CatalogCard(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .fillMaxHeight(0.5f),
                            data = item[0],
                            onAdd = onAddCartItem
                        )
                    }
                } else {
                    CatalogCard(
                        modifier = Modifier
                            .padding(top = 240.dp)
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.85f),
                        data = item[0],
                        onAdd = onAddCartItem
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
                Catalog(items = SampleItems.filter { it.category == Category.Accessories })
            }
        }
    }
}