package org.d3if3132.assesment02.readact.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.d3if3132.assesment02.readact.navigation.BottomBarNavGraph

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarNavGraph.Home,
        BottomBarNavGraph.Search,
        BottomBarNavGraph.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any {
        it.route == currentDestination?.route
    }
    if (bottomBarDestination){
        NavigationBar {
            screens.forEach{screen ->
                NavigationBarItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.NavigationBarItem(
    screen: BottomBarNavGraph,
    currentDestination: NavDestination?,
    navController: NavHostController,
    hierarchy: Boolean = currentDestination?.hierarchy?.any { it.route == screen.route } == true
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title, color = if (hierarchy) MaterialTheme.colorScheme.tertiary else Color.Gray)
        },
        icon = {
            Icon(
                imageVector = screen.icon, contentDescription = "Navigation Icon"
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.tertiary,
            unselectedIconColor = Color.Gray
        ),
        selected = hierarchy,

        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}