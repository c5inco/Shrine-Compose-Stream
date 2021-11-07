package com.example.shrinecompose

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

@ExperimentalMaterialApi
@Composable
fun Backdrop() {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)

    BackdropScaffold(
        scaffoldState = scaffoldState,
        gesturesEnabled = false,
        appBar = {
            Text("This is the content for appBar")
        },
        frontLayerContent = {
            Text("This is the content for frontLayer")
        },
        backLayerContent = {
            Text("This is the content for backLayer")
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