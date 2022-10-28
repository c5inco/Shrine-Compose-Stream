/*
 * Copyright 2022 Google LLC
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
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@Composable
private fun CatalogCard(
    modifier: Modifier = Modifier,
    data: ItemData,
    onAdd: (FirstAddCartItemData) -> Unit
) {
    var imageSize by remember { mutableStateOf(IntSize.Zero) }
    var position by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = modifier
            .onGloballyPositioned {
                position = it.positionInRoot()
            }
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    onAdd(FirstAddCartItemData(data, imageSize, position))
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
                    .onGloballyPositioned {
                        imageSize = it.size
                    }
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
                    .offset { IntOffset(0, y = 12.dp.roundToPx()) }
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
    onAddCartItem: (FirstAddCartItemData) -> Unit = {}
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