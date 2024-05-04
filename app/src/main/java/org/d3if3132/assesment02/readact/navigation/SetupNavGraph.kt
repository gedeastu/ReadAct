package org.d3if3132.assesment02.readact.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import org.d3if3132.assesment02.readact.ui.screen.KEY_ID_CATATAN

sealed class AuthNavGraph(val route : String){
    data object Splash : AuthNavGraph(Route.SPLASH)
    data object Login : AuthNavGraph(Route.LOGIN_SCREEN)
}

sealed class AddEditNavGraph(val route: String){
    data object AddScreen : AddEditNavGraph(Route.ADDEDIT_SCREEN)
    data object EditScreen : AddEditNavGraph("${Route.ADDEDIT_SCREEN}/{$KEY_ID_CATATAN}"){
        fun withId(id:Long) = "${Route.ADDEDIT_SCREEN}/$id"
    }
}

sealed class DetailNavGraph(val route: String){
    data object DetailScreen : DetailNavGraph("${Route.DETAIL_SCREEN}/{$KEY_ID_CATATAN}"){
        fun withId(id: Long) = "${Route.DETAIL_SCREEN}/$id"
    }
}

sealed class BottomBarNavGraph (
    val route: String,
    val title: String,
    val icon: ImageVector
){
    data object Home: BottomBarNavGraph(
        route = Route.HOME_SCREEN,
        title = "Home",
        icon = Icons.Default.Home
    )
    data object Search: BottomBarNavGraph(
        route = Route.SEARCH_SCREEN,
        title = "Search",
        icon = Icons.Default.Search
    )
    data object Profile: BottomBarNavGraph(
        route = Route.PROFILE_SCREEN,
        title = "Profile",
        icon = Icons.Default.AccountCircle
    )
}