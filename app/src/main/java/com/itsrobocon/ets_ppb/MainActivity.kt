package com.itsrobocon.ets_ppb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.itsrobocon.ets_ppb.navigation.LibraryNavGraph
import com.itsrobocon.ets_ppb.ui.theme.ETSPPBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ETSPPBTheme {
                LibraryNavGraph()
            }
        }
    }
}
