package com.example.shrinecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.shrinecompose.ui.theme.ShrineComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShrineComposeTheme {
                Cart()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ShrineComposeTheme {
        Cart()
    }
}