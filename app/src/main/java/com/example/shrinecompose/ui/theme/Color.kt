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

package com.example.shrinecompose.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val ShrinePink10 = Color(0xfffffbfa)
val ShrinePink50 = Color(0xfffeeae6)
val ShrinePink100 = Color(0xfffedbd0)
val ShrinePink300 = Color(0xfffff0ea)
val ShrinePink500 = Color(0xfffbb8ac)
val ShrinePink900 = Color(0xff442c2e)

val ShrineScrimColor = ShrinePink300

internal val ShrineLightColorPalette = lightColors(
    primary = ShrinePink100,
    primaryVariant = ShrinePink500,
    secondary = ShrinePink50,
    background = ShrinePink100,
    surface = ShrinePink10,
    error = Color(0xffc5032b),
    onPrimary = ShrinePink900,
    onSecondary = ShrinePink900,
    onBackground = ShrinePink900,
    onSurface = ShrinePink900,
    onError = ShrinePink10
)