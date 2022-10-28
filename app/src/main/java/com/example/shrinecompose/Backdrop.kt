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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme
import com.example.shrinecompose.ui.theme.ShrineScrimColor
import kotlinx.coroutines.launch

@Composable
private fun TopAppBarText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text.uppercase(),
        style = MaterialTheme.typography.subtitle1,
        fontSize = 17.sp
    )
}

@Composable
private fun MenuSearchField() {
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .height(56.dp)
            .padding(end = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(end = 36.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
            }
        )
        if (searchText.isEmpty()) {
            TopAppBarText(
                modifier = Modifier.alpha(ContentAlpha.disabled),
                text = "Search Shrine"
            )
        }
        Divider(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun ShrineTopAppBar(
    backdropRevealed: Boolean,
    onBackdropReveal: (Boolean) -> Unit = {}
) {
    TopAppBar(
        title = {
            val density = LocalDensity.current

            Box(
                Modifier
                    .width(46.dp)
                    .fillMaxHeight()
                    .toggleable(
                        value = backdropRevealed,
                        onValueChange = { onBackdropReveal(it) },
                        indication = rememberRipple(bounded = false, radius = 56.dp),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                AnimatedVisibility(
                    visible = !backdropRevealed,
                    enter = fadeIn(animationSpec = tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing))
                            + slideInHorizontally(initialOffsetX = { with(density) { (-20).dp.roundToPx() } }, animationSpec = tween(durationMillis = 270, easing = LinearEasing)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 120, easing = LinearEasing))
                            + slideOutHorizontally(targetOffsetX = { with(density) { (-20).dp.roundToPx() } }, animationSpec = tween(durationMillis = 120, easing = LinearEasing)),
                    label = "Menu navigation icon"
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_menu_cut_24px),
                        contentDescription = "Menu navigation icon"
                    )
                }

                val logoTransition = updateTransition(
                    targetState = backdropRevealed,
                    label = "logoTransition"
                )
                val logoOffset by logoTransition.animateDp(
                    transitionSpec = {
                        if (targetState) {
                            tween(durationMillis = 120, easing = LinearEasing)
                        } else {
                            tween(durationMillis = 270, easing = LinearEasing)
                        }
                    },
                    label = "logoOffset"
                ) { revealed ->
                    if (!revealed) 20.dp else 0.dp
                }

                Icon(
                    painterResource(id = R.drawable.ic_shrine_logo),
                    contentDescription = "Shrine logo",
                    modifier = Modifier.offset(x = logoOffset)
                )
            }

            AnimatedContent(
                targetState = backdropRevealed,
                transitionSpec = {
                    if (targetState) {
                        // Conceal to reveal
                        fadeIn(animationSpec = tween(durationMillis = 240, delayMillis = 120, easing = LinearEasing)) +
                            slideInHorizontally(initialOffsetX = { with(density) { 30.dp.roundToPx() } }, animationSpec = tween(durationMillis = 270, easing = LinearEasing)) with
                        fadeOut(animationSpec = tween(durationMillis = 120, easing = LinearEasing)) +
                            slideOutHorizontally(targetOffsetX = { with(density) { (-30).dp.roundToPx() } }, animationSpec = tween(durationMillis = 120, easing = LinearEasing))
                    } else {
                        // Reveal to conceal
                        fadeIn(animationSpec = tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing)) +
                            slideInHorizontally(initialOffsetX = { with(density) { (-30).dp.roundToPx() } }, animationSpec = tween(durationMillis = 270, easing = LinearEasing)) with
                        fadeOut(animationSpec = tween(durationMillis = 90, easing = LinearEasing)) +
                            slideOutHorizontally(targetOffsetX = { with(density) { 30.dp.roundToPx() } }, animationSpec = tween(durationMillis = 90, easing = LinearEasing))
                    }.using(SizeTransform(clip = false))
                },
                contentAlignment = Alignment.CenterStart
            ) { revealed ->
                if (!revealed) {
                    TopAppBarText(text = "Shrine")
                } else {
                    MenuSearchField()
                }
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search action",
                tint = LocalContentColor.current.copy(alpha = ContentAlpha.high),
                modifier = Modifier
                    .padding(end = 12.dp)
            )
        },
        elevation = 0.dp
    )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ShrineTopAppBarPreview() {
    ShrineComposeTheme {
        Surface {
            Column(Modifier.padding(20.dp)) {
                ShrineTopAppBar(false)
                Spacer(Modifier.height(8.dp))
                ShrineTopAppBar(true)
            }
        }
    }
}

@Composable
private fun MenuText(
    text: String = "Item",
    activeDecoration: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier.height(44.dp),
        contentAlignment = Alignment.Center
    ) {
        activeDecoration()
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.MenuItem(
    modifier: Modifier = Modifier,
    index: Int,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .animateEnterExit(
                enter = fadeIn(animationSpec = tween(durationMillis = 240, delayMillis = index * 15 + 60, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 90, easing = LinearEasing)),
                label = "Menu item $index"
            )
            .fillMaxWidth(0.5f)
            .clip(MaterialTheme.shapes.medium)
            .then(modifier)
    ) {
        content()
    }
}

@ExperimentalAnimationApi
@Composable
private fun NavigationMenu(
    modifier: Modifier = Modifier,
    backdropRevealed: Boolean = true,
    activeCategory: Category = Category.All,
    onMenuSelect: (Category) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = backdropRevealed,
            enter = EnterTransition.None,
            exit = ExitTransition.None,
            label = "Navigation menu"
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categories = Category.values()

                categories.forEachIndexed { idx, category ->
                    MenuItem(
                        modifier = Modifier.clickable { onMenuSelect(category) },
                        index = idx
                    ) {
                        MenuText(
                            text = category.toString(),
                            activeDecoration = {
                                if (category == activeCategory) {
                                    Image(
                                        painterResource(id = R.drawable.ic_tab_indicator),
                                        contentDescription = "Active category icon"
                                    )
                                }
                            }
                        )
                    }
                }
                MenuItem(index = categories.size) {
                    Divider(
                        modifier = Modifier
                            .width(56.dp)
                            .padding(vertical = 12.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                }
                MenuItem(index = categories.size + 1) {
                    MenuText("Logout")
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun NavigationMenuPreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            var activeCategory by remember { mutableStateOf(Category.Accessories) }
            NavigationMenu(
                modifier = Modifier.padding(vertical = 8.dp),
                activeCategory = activeCategory,
                onMenuSelect = { activeCategory = it }
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Backdrop(
    showScrim: Boolean = false,
    onAddCartItem: (FirstAddCartItemData) -> Unit = {},
    onBackdropReveal: (Boolean) -> Unit = {}
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var backdropRevealed by rememberSaveable { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()
    var activeCategory by rememberSaveable { mutableStateOf(Category.All) }

    BackdropScaffold(
        scaffoldState = scaffoldState,
        gesturesEnabled = false,
        appBar = {
            ShrineTopAppBar(
                backdropRevealed = backdropRevealed,
                onBackdropReveal = {
                    if (!scaffoldState.isAnimationRunning) {
                        backdropRevealed = it
                        onBackdropReveal(it)
                        scope.launch {
                            if (scaffoldState.isConcealed) {
                                scaffoldState.reveal()
                            } else {
                                scaffoldState.conceal()
                            }
                        }
                    }
                }
            )
        },
        frontLayerContent = {
            val scrimAlpha by animateFloatAsState(
                targetValue = if (showScrim) 0.6f else 0f,
                animationSpec = tween(durationMillis = 500, easing = LinearEasing)
            )
            Catalog(
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawRect(color = ShrineScrimColor, alpha = scrimAlpha)
                },
                items = SampleItems.filter {
                    activeCategory == Category.All || it.category == activeCategory
                },
                onAddCartItem = onAddCartItem
            )
        },
        frontLayerShape = MaterialTheme.shapes.large,
        frontLayerElevation = 16.dp,
        frontLayerScrimColor = ShrineScrimColor.copy(alpha = 0.6f),
        backLayerContent = {
            NavigationMenu(
                modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
                backdropRevealed = backdropRevealed,
                activeCategory = activeCategory,
                onMenuSelect = {
                    backdropRevealed = false
                    onBackdropReveal(false)
                    activeCategory = it
                    scope.launch { scaffoldState.conceal() }
                }
            )
        },
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun BackdropPreview() {
    ShrineComposeTheme {
        Backdrop()
    }
}