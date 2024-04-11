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

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
@ExperimentalMaterialApi
@Composable
fun ShrineApp() {
    var sheetState by rememberSaveable { mutableStateOf(CartBottomSheetState.Collapsed) }
    // val cartItems = remember { mutableStateListOf(*SampleItems.take(2).toTypedArray()) }
    val cartItems = remember { mutableStateListOf<ItemData>() }
    var onboardingState by remember { mutableStateOf(OnboardedState.Start) }

    SharedTransitionLayout {
        BoxWithConstraints(
            Modifier.fillMaxSize()
        ) {
            Backdrop(
                showScrim = sheetState == CartBottomSheetState.Expanded,
                onboardedState = onboardingState,
                onAddCartItem = {
                    onboardingState = OnboardedState.End
                    cartItems.add(it)
                },
                onBackdropReveal = { revealed ->
                    sheetState = if (revealed) CartBottomSheetState.Hidden else CartBottomSheetState.Collapsed
                }
            )
            CartBottomSheet(
                modifier = Modifier.align(Alignment.BottomEnd),
                items = cartItems,
                sheetState = sheetState,
                maxHeight = maxHeight,
                maxWidth = maxWidth,
                onboardingState = onboardingState,
                onRemoveItemFromCart = {
                    cartItems.removeAt(it)
                },
                onSheetStateChange = {
                    sheetState = it
                }
            )
        }
    }
}

enum class CartBottomSheetState {
    Collapsed,
    Expanded,
    Hidden,
}

enum class OnboardedState {
    Start,
    End
}