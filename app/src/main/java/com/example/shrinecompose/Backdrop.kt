package com.example.shrinecompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@ExperimentalMaterialApi
@Composable
fun Backdrop() {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)

    BackdropScaffold(
        scaffoldState = scaffoldState,
        gesturesEnabled = false,
        appBar = {
            TopAppBar(
                title = {
                    Box(
                        Modifier
                            .width(46.dp)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_menu_cut_24px),
                            contentDescription = "Menu navigation icon"
                        )
                        Icon(
                            painterResource(id = R.drawable.ic_shrine_logo),
                            contentDescription = "Shrine logo",
                            modifier = Modifier.offset(x = 20.dp)
                        )
                    }

                    Text(
                        "Shrine".uppercase(),
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 17.sp
                    )
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
        },
        frontLayerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text("This is the content for frontLayer")
            }
        },
        backLayerContent = {
            Column(Modifier.padding(20.dp)) {
                Text("This is the content for backLayer")
            }
        },
        frontLayerShape = MaterialTheme.shapes.large,
        frontLayerElevation = 16.dp
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun BackdropPreview() {
    ShrineComposeTheme {
        Backdrop()
    }
}