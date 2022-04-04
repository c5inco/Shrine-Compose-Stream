package com.example.shrinecompose

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.shrinecompose.ui.theme.ShrineScrimColor

enum class NewCartItemState {
    Initial,
    Added
}

data class NewCartItemData(
    val data: ItemData,
    val imageSize: IntSize,
    val cardOffset: Offset
)

@Composable
fun NewCartItem(
    modifier: Modifier = Modifier,
    data: NewCartItemData,
    onTap: () -> Unit
) {
    val (itemData, imageSize, cardOffset) = data
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val currentState = remember { MutableTransitionState(NewCartItemState.Initial) }
    currentState.targetState = NewCartItemState.Added

    val addAnimation = updateTransition(
        transitionState = currentState, label = "Add to cart animation"
    )
    val itemAlpha by addAnimation.animateFloat(
        transitionSpec = { tween(durationMillis = 300) },
        label = "Scrim fade"
    ) {
        if (it == NewCartItemState.Initial) 0f else 1f
    }

    val itemSize by addAnimation.animateSize(
        transitionSpec = { tween(durationMillis = 500, delayMillis = 400, easing = FastOutLinearInEasing) },
        label = "Item size"
    ) {
        if (it == NewCartItemState.Initial) {
            imageSize.toSize()
        } else {
            with (LocalDensity.current) {
                Size(40.dp.toPx(), 40.dp.toPx())
            }
        }
    }
    val itemCornerSize by addAnimation.animateDp(
        transitionSpec = { tween(durationMillis = 1000, delayMillis = 400, easing = LinearOutSlowInEasing) },
        label = "Item corner size"
    ) {
        if (it == NewCartItemState.Initial) 0.dp else 10.dp
    }
    val scrimAlpha by addAnimation.animateFloat(
        transitionSpec = { tween(durationMillis = 1000, delayMillis = 400, easing = LinearOutSlowInEasing) },
        label = "Scrim fade"
    ) {
        if (it == NewCartItemState.Initial) 0.6f else 0f
    }

    val itemOffsetX by addAnimation.animateFloat(
        transitionSpec = { tween(durationMillis = 500, delayMillis = 400) },
        label = "Item offsetX"
    ) {
        if (it == NewCartItemState.Initial) {
            cardOffset.x
        } else {
            with (LocalDensity.current) {
                (screenWidth - 40 - 16).dp.toPx()
            }
        }
    }
    val itemOffsetY by addAnimation.animateFloat(
        transitionSpec = {
            tween(durationMillis = 500, delayMillis = 550, easing = FastOutLinearInEasing)
        },
        label = "Item offsetY"
    ) {
        if (it == NewCartItemState.Initial) {
            cardOffset.y
        } else {
            with (LocalDensity.current) {
                (screenHeight - 40 - 8).dp.toPx()
            }
        }
    }
    val itemElevation by addAnimation.animateDp(
        transitionSpec = { tween(durationMillis = 500, delayMillis = 400) },
        label = "Scrim fade"
    ) {
        if (it == NewCartItemState.Initial) 16.dp else 0.dp
    }

    Box(
        modifier = modifier
            .offset { IntOffset(itemOffsetX.toInt(), itemOffsetY.toInt()) }
            .graphicsLayer { alpha = itemAlpha }
            .clickable { onTap() }
    ) {
        Image(
            painter = painterResource(id = itemData.photoResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(itemSize.toDpSize())
                .drawWithContent {
                    drawContent()
                    drawRect(ShrineScrimColor.copy(alpha = scrimAlpha))
                }
                .shadow(itemElevation, RoundedCornerShape(itemCornerSize))
                .clip(RoundedCornerShape(itemCornerSize))
        )
    }
}