package org.d3if3132.assesment02.readact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import org.d3if3132.assesment02.readact.ui.presentation.sign_in.GoogleAuthUIClient
import org.d3if3132.assesment02.readact.ui.theme.ReadActTheme

class MainActivity : ComponentActivity() {
    private val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadActTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme

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