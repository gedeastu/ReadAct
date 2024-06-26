package org.d3if3132.assesment02.readact.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.d3if3132.assesment02.readact.ui.components.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController,content:@Composable (Modifier)->Unit) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navHostController)
        }
    ){paddingValues ->
        content(Modifier.padding(paddingValues = paddingValues))
    }
}