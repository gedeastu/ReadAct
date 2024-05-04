package org.d3if3132.assesment02.readact.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.d3if3132.assesment02.readact.model.SignInState

@Composable
fun LoginScreen(state: SignInState, onSignInClick: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError, block = {
        state.signInError?.let{ error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    })

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(modifier = Modifier){
            Text(text = "LOGIN")
            Button(onClick = {
                onSignInClick()
            }) {
                Text(text = "Login with Google")
            }
        }
    }
}