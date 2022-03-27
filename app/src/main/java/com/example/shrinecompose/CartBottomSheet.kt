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

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.SampleData.Companion.SampleItems
import com.example.shrinecompose.ui.theme.ShrineComposeTheme
import kotlin.math.min

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp)
            ) {
                items.forEach { item ->
                    CartItem(item)
                }
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
private fun CheckoutButton() {
    Button(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = "Shopping cart icon"
        )
        Spacer(Modifier.width(16.dp))
        Text("Proceed to checkout".uppercase())
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

enum class CartBottomSheetState {
    Collapsed,
    Expanded,
    Hidden,
}

@ExperimentalAnimationApi
@Composable
fun CartBottomSheet(
    modifier: Modifier = Modifier,
    items: List<ItemData> = SampleItems,
    hidden: Boolean = false,
    maxHeight: Dp,
    maxWidth: Dp,
) {
    var expanded by remember { mutableStateOf(false) }

    val cartTransition = updateTransition(
        targetState = when {
            hidden -> CartBottomSheetState.Hidden
            expanded -> CartBottomSheetState.Expanded
            else -> CartBottomSheetState.Collapsed
        },
        label = "cartTransition"
    )

    val cartXOffset by cartTransition.animateDp(
        label = "cartXOffset",
        transitionSpec = {
            when {
                CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                    tween(durationMillis = 433, delayMillis = 67)
                CartBottomSheetState.Collapsed isTransitioningTo CartBottomSheetState.Expanded ->
                    tween(durationMillis = 150)
                else ->
                    tween(durationMillis = 450)
            }
        }
    ) {
        when (it) {
            CartBottomSheetState.Hidden -> maxWidth
            CartBottomSheetState.Expanded -> 0.dp
            else -> {
                val size = min(2, items.size)
                var width = 24 + 40 * (size + 1) + 16 * size + 16
                (maxWidth.value - (width)).dp
            }
        }
    }

    val cartHeight by cartTransition.animateDp(
        label = "cartHeight",
        transitionSpec = {
            when {
                CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                    tween(durationMillis = 283)
                else ->
                    tween(durationMillis = 500)
            }
        }
    ) {
        if (it == CartBottomSheetState.Expanded) maxHeight else 56.dp
    }

    val cornerSize by cartTransition.animateDp(
        label = "cartCornerSize",
        transitionSpec = {
            when {
                CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                    tween(durationMillis = 433, delayMillis = 67)
                else ->
                    tween(durationMillis = 150)
            }
        }
    ) {
        if (it == CartBottomSheetState.Expanded) 0.dp else 24.dp
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .offset(x = cartXOffset)
            .height(cartHeight),
        shape = CutCornerShape(topStart = cornerSize),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp
    ) {
        Box {
            cartTransition.AnimatedContent(
                transitionSpec = {
                    when {
                        CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                            fadeIn(animationSpec = tween(durationMillis = 117, delayMillis = 117, easing = LinearEasing)) with
                                fadeOut(animationSpec = tween(durationMillis = 117, easing = LinearEasing))
                        CartBottomSheetState.Collapsed isTransitioningTo CartBottomSheetState.Expanded ->
                            fadeIn(animationSpec = tween(durationMillis = 150, delayMillis = 150, easing = LinearEasing)) with
                                fadeOut(animationSpec = tween(durationMillis = 150, easing = LinearEasing))
                        else -> EnterTransition.None with ExitTransition.None
                    }.using(SizeTransform(clip = false))
                },
            ) { targetState ->
                if (targetState == CartBottomSheetState.Expanded) {
                    ExpandedCart(
                        items = items,
                        onCollapse = { expanded = false }
                    )
                } else {
                    CollapsedCart(
                        items = items.subList(fromIndex = 0, toIndex = 2),
                        onTap = { expanded = true }
                    )
                }
            }
            cartTransition.AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = { it == CartBottomSheetState.Expanded },
                enter = fadeIn(animationSpec = tween(durationMillis = 150, delayMillis = 150, easing = LinearEasing)) +
                    scaleIn(animationSpec = tween(durationMillis = 250, delayMillis = 250, easing = LinearOutSlowInEasing), initialScale = 0.8f),
                exit = fadeOut(animationSpec = tween(durationMillis = 117, easing = LinearEasing)) +
                    scaleOut(animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing), targetScale = 0.8f)
            ) {
                CheckoutButton()
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(device = Devices.PIXEL_4)
@Composable
fun CartBottomSheetPreview() {
    ShrineComposeTheme {
        BoxWithConstraints(
            Modifier.fillMaxSize()
        ) {
            var isBottomSheetHidden by remember { mutableStateOf(false) }

            Button(onClick = { isBottomSheetHidden = !isBottomSheetHidden}) {
                Text("Toggle BottomSheet")
            }

            CartBottomSheet(
                modifier = Modifier.align(Alignment.BottomEnd),
                hidden = isBottomSheetHidden,
                maxHeight = maxHeight,
                maxWidth = maxWidth
            )
        }
    }
}