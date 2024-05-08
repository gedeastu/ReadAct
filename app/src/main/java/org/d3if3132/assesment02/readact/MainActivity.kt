package org.d3if3132.assesment02.readact

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import org.d3if3132.assesment02.readact.navigation.AddEditNavGraph
import org.d3if3132.assesment02.readact.navigation.AuthNavGraph
import org.d3if3132.assesment02.readact.navigation.BottomBarNavGraph
import org.d3if3132.assesment02.readact.navigation.DetailNavGraph
import org.d3if3132.assesment02.readact.navigation.Route
import org.d3if3132.assesment02.readact.ui.presentation.sign_in.GoogleAuthUIClient
import org.d3if3132.assesment02.readact.ui.presentation.sign_in.SignInViewModel
import org.d3if3132.assesment02.readact.ui.screen.AddEditScreen
import org.d3if3132.assesment02.readact.ui.screen.DetailScreen
import org.d3if3132.assesment02.readact.ui.screen.HomeScreen
import org.d3if3132.assesment02.readact.ui.screen.KEY_ID_BOOK
import org.d3if3132.assesment02.readact.ui.screen.LoginScreen
import org.d3if3132.assesment02.readact.ui.screen.MainScreen
import org.d3if3132.assesment02.readact.ui.screen.ProfileScreen
import org.d3if3132.assesment02.readact.ui.screen.SearchScreen
import org.d3if3132.assesment02.readact.ui.theme.ReadActTheme

class MainActivity : ComponentActivity() {
    private val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadActTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                MainScreen(navHostController = navController,content = {modifier ->
                    Box(modifier = modifier.fillMaxSize()){
                    NavHost(navController = navController, startDestination = Route.AUTHENTICATION, route = Route.ROOT){

                        navigation(startDestination = AuthNavGraph.Login.route, route = Route.AUTHENTICATION){
                            composable(route = AuthNavGraph.Login.route){

                                val viewModel = viewModel<SignInViewModel>()
                                val state by viewModel.state.collectAsStateWithLifecycle()

                                LaunchedEffect(key1 = Unit) {
                                    if (googleAuthUIClient.getSignedInUser() != null) {
                                        navController.navigate(Route.MAIN)
                                    }
                                }

                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                                    onResult = { result ->
                                        if (result.resultCode == RESULT_OK) {
                                            lifecycleScope.launch {
                                                val signInResult =
                                                    googleAuthUIClient.signInWithIntent(
                                                        intent = result.data ?: return@launch
                                                    )
                                                viewModel.onSignInResult(signInResult)
                                            }
                                        }
                                    }
                                )

                                LaunchedEffect(key1 = state.isSignSuccessful, block = {
                                    if (state.isSignSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign in Successful",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.navigate(Route.MAIN){

                                        }

                                        viewModel.resetState()
                                    }
                                })
                                    LoginScreen(
                                        state = state,
                                        onSignInClick = {
                                            lifecycleScope.launch {
                                                val signInIntentSender = googleAuthUIClient.signIn()
                                                launcher.launch(
                                                    IntentSenderRequest.Builder(
                                                        signInIntentSender ?: return@launch
                                                    ).build()
                                                )
                                            }
                                        })
                            }
                        }

                        navigation(startDestination = BottomBarNavGraph.Home.route, route = Route.MAIN){
                            composable(route = BottomBarNavGraph.Home.route){
                                HomeScreen(navController = navController)
                            }

                            composable(route = BottomBarNavGraph.Search.route){
                                SearchScreen(navController = navController)
                            }

                            composable(route = BottomBarNavGraph.Profile.route){
                                ProfileScreen(userData = googleAuthUIClient.getSignedInUser(), onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUIClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed Out",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate(AuthNavGraph.Login.route)
                                    }
                                })
                            }

                            composable(route = AddEditNavGraph.AddScreen.route){
                                AddEditScreen(navController = navController)
                            }

                            composable(
                                route = AddEditNavGraph.EditScreen.route,
                                arguments = listOf(
                                    navArgument(KEY_ID_BOOK){ type = NavType.LongType }
                                )
                            ){navBackStackEntry ->
                                val dataId = navBackStackEntry.arguments?.getLong(KEY_ID_BOOK)
                                AddEditScreen(navController = navController, id = dataId)
                            }

                            composable(route = DetailNavGraph.DetailScreen.route, arguments = listOf(
                                navArgument(KEY_ID_BOOK){ type = NavType.LongType }
                            )){navBackStackEntry ->
                                val dataId = navBackStackEntry.arguments?.getLong(KEY_ID_BOOK)
                                DetailScreen(id = dataId)
                            }
                        }
                    }}
                })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReadActTheme {

    }
}