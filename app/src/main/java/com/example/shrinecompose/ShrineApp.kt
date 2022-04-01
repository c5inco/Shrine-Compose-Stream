package com.example.shrinecompose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ShrineApp() {
    var isBottomSheetHidden by remember { mutableStateOf(false) }
    var isBottomSheetExpanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        Modifier.fillMaxSize()
    ) {
        Backdrop(
            showScrim = isBottomSheetExpanded
        ) {
            isBottomSheetHidden = it
        }
        CartBottomSheet(
            modifier = Modifier.align(Alignment.BottomEnd),
            hidden = isBottomSheetHidden,
            maxHeight = maxHeight,
            maxWidth = maxWidth
        ) {
            isBottomSheetExpanded = it
        }
    }
}