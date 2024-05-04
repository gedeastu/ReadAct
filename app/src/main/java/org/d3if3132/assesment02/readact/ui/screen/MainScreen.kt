package org.d3if3132.assesment02.readact.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.d3if3132.assesment02.readact.ui.components.BottomBar

@Composable
fun MainScreen(navHostController: NavHostController,content:@Composable (Modifier)->Unit) {
    Scaffold(bottomBar = {
        BottomBar()
    }){paddingValues ->
        content(Modifier.padding(paddingValues = paddingValues))
    }
}