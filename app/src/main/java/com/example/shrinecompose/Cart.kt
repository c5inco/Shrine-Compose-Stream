package com.example.shrinecompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@Composable
fun Cart() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val cartSize = 5

        Row(
            Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Cart".uppercase(),
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.width(12.dp))
            Text("$cartSize items".uppercase())
        }

        // Items
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 1..cartSize) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
                        Row(
                            Modifier.padding(vertical = 20.dp)
                        ) {
                            Column(
                                Modifier.padding(end = 16.dp)
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Vendor name $i".uppercase(),
                                        style = MaterialTheme.typography.body2,
                                    )
                                    Text(
                                        text = "$100.00",
                                        style = MaterialTheme.typography.body2,
                                    )
                                }
                                Text(
                                    text = "Awesome item",
                                    style = MaterialTheme.typography.subtitle2,
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = "Add to card icon"
            )
            Spacer(Modifier.width(16.dp))
            Text("Add to cart".uppercase())
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun CartPreview() {
    ShrineComposeTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colors.secondary
        ) {
            Cart()
        }
    }
}