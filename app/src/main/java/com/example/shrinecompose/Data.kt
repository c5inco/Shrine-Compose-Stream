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

data class ItemData(
    val id: Int,
    val title: String,
    val price: Int,
    val vendor: Vendor,
    val photoResId: Int,
)

enum class Vendor {
    Alphi,
    Lmbrjk,
    Mal,
    Six,
    Squiggle,
}

enum class Category {
    All,
    Accessories,
    Clothing,
    Home
}

val SampleItems = listOf(
    ItemData(
        id = 0,
        title = "Vagabond sack",
        price = 120,
        vendor = Vendor.Squiggle,
        photoResId = R.drawable.photo_0
    ),
    ItemData(
        id = 1,
        title = "Stella sunglasses",
        price = 58,
        vendor = Vendor.Mal,
        photoResId = R.drawable.photo_1
    ),
    ItemData(
        id = 2,
        title = "Whitney belt",
        price = 35,
        vendor = Vendor.Lmbrjk,
        photoResId = R.drawable.photo_2
    ),
    ItemData(
        id = 3,
        title = "Garden stand",
        price = 98,
        vendor = Vendor.Alphi,
        photoResId = R.drawable.photo_3
    ),
    ItemData(
        id = 4,
        title = "Strut earrings",
        price = 34,
        vendor = Vendor.Six,
        photoResId = R.drawable.photo_4
    ),
    ItemData(
        id = 5,
        title = "Varsity socks",
        price = 12,
        vendor = Vendor.Lmbrjk,
        photoResId = R.drawable.photo_5
    ),
    ItemData(
        id = 6,
        title = "Weave key ring",
        price = 16,
        vendor = Vendor.Six,
        photoResId = R.drawable.photo_6
    ),
    ItemData(
        id = 7,
        title = "Gatsby hat",
        price = 40,
        vendor = Vendor.Six,
        photoResId = R.drawable.photo_7
    ),
    ItemData(
        id = 8,
        title = "Shrug bag",
        price = 198,
        vendor = Vendor.Squiggle,
        photoResId = R.drawable.photo_8
    ),
    ItemData(
        id = 9,
        title = "Gilt desk trio",
        price = 58,
        vendor = Vendor.Alphi,
        photoResId = R.drawable.photo_9
    ),
)
