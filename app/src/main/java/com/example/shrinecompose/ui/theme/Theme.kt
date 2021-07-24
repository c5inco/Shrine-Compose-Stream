package com.example.shrinecompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShrineComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        // TODO: Add dark colors in the future
        ShrineLightColorPalette
    } else {
        ShrineLightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview(name = "Theme test", showBackground = true)
@Composable
fun ThemeTest() {
    Column(
        Modifier.padding(48.dp),
    ) {
        ShrineComposeTheme {
            Button(onClick = {}) {
                Text("Button1")
            }
            Spacer(Modifier.height(16.dp))
            Card {
                Column(
                    Modifier.padding(16.dp)
                ) {
                    Text("This is a card")
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        MaterialTheme {
            Button(onClick = {}) {
                Text("Button1")
            }
            Spacer(Modifier.height(16.dp))
            Card {
                Column(
                    Modifier.padding(16.dp)
                ) {
                    Text("This is a card")
                }
            }
        }
    }
}

@Preview(name = "Typography test", widthDp = 720, showBackground = true)
@Composable
fun TypographyThemeTest() {
    ShrineComposeTheme {
        Column {
            Text(
                "H1 / Rubik Light",
                style = MaterialTheme.typography.h1
            )
            Text("H2 / Rubik Light",
                style = MaterialTheme.typography.h2
            )
            Text("H3 / Rubik Regular",
                style = MaterialTheme.typography.h3
            )
            Text("Body1 / Rubik Regular",
                style = MaterialTheme.typography.body1
            )
            Text("Button / Rubik Medium".toUpperCase(),
                style = MaterialTheme.typography.button
            )
            Text("Caption / Rubik Regular",
                style = MaterialTheme.typography.caption
            )
        }
    }
}