package com.example.shrinecompose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shrinecompose.ui.theme.ShrineComposeTheme
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

@Composable
private fun ShrineTopAppBar(
    backdropRevealed: Boolean,
    onBackdropReveal: (Boolean) -> Unit = {}
) {
    TopAppBar(
        title = {
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
                if (!backdropRevealed) {
                    Icon(
                        painterResource(id = R.drawable.ic_menu_cut_24px),
                        contentDescription = "Menu navigation icon"
                    )
                }
                Icon(
                    painterResource(id = R.drawable.ic_shrine_logo),
                    contentDescription = "Shrine logo",
                    modifier = Modifier.offset(x = if (!backdropRevealed) 20.dp else 0.dp)
                )
            }

            if (!backdropRevealed) {
                TopAppBarText(text = "Shrine")
            } else {
                MenuSearchField()
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

@ExperimentalMaterialApi
@Composable
fun Backdrop() {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var backdropRevealed by remember { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()

    BackdropScaffold(
        scaffoldState = scaffoldState,
        gesturesEnabled = false,
        appBar = {
            ShrineTopAppBar(
                backdropRevealed = backdropRevealed,
                onBackdropReveal = {
                    backdropRevealed = it
                    scope.launch {
                        if (scaffoldState.isConcealed) {
                            scaffoldState.reveal()
                        } else {
                            scaffoldState.conceal()
                        }
                    }
                }
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