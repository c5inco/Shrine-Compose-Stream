package com.example.shrinecompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@Composable
fun Backdrop() {
    Box(Modifier.fillMaxSize()) {
        BackLayer()
    }
}

@Composable
fun BackLayer() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        var inFocus by remember { mutableStateOf(false) }
        val logoOffset = if (!inFocus) 20 else 0

        Column {
            TopAppBar(
                title = {
                    Box(
                        Modifier
                            .width(46.dp)
                            .fillMaxHeight()
                            .clickable(
                                onClick = { inFocus = !inFocus },
                                indication = rememberRipple(bounded = false, radius = 56.dp),
                                interactionSource = remember { MutableInteractionSource() }
                            )
                        ,
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (!inFocus) {
                            Icon(
                                painterResource(id = R.drawable.ic_menu_cut_24px),
                                contentDescription = "Menu navigation icon"
                            )
                        }
                        Icon(
                            painterResource(id = R.drawable.ic_shrine_logo),
                            contentDescription = "Shrine logo",
                            modifier = Modifier.offset(x = logoOffset.dp)
                        )
                    }
                    Box {
                        if (inFocus) {
                            Text(
                                text = "Menu".uppercase(),
                                style = MaterialTheme.typography.subtitle1,
                                fontSize = 17.sp
                            )
                        } else {
                            Text(
                                text = "Shrine".uppercase(),
                                style = MaterialTheme.typography.subtitle1,
                                fontSize = 17.sp
                            )
                        }
                    }
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search action",
                        tint = LocalContentColor.current.copy(alpha = ContentAlpha.high)
                    )
                    Spacer(Modifier.width(12.dp))
                },
                elevation = 0.dp
            )
        }
    }
}

@Preview
@Composable
fun BackdropPreview() {
    ShrineComposeTheme {
        Backdrop()
    }
}