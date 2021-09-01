package com.judrummer.pickupcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.judrummer.pickupcompose.ui.screen.pickuplist.PickUpListScreen
import com.judrummer.pickupcompose.ui.theme.PickUpComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickUpComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PickUpListScreen()
                }
            }
        }
    }
}
