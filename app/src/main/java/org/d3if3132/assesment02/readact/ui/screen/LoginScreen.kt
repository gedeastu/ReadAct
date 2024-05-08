package org.d3if3132.assesment02.readact.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.model.SignInState

@Composable
fun LoginScreen(state: SignInState, onSignInClick: () -> Unit) {
    var isLoading by remember {
        mutableStateOf(false)
    }
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
    if (isLoading){
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }else{
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Column(modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom){
                Text(text = "WELCOME TO READACT", fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center,fontSize = 25.sp,color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 20.dp))
                Image(painter = painterResource(id =R.drawable.book_stack), contentDescription = null, modifier = Modifier.padding(bottom = 20.dp))
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween){
                OutlinedButton(onClick = {
                    try {
                        isLoading = true
                        onSignInClick()
                    }catch (e:Exception){
                        println(e)
                    }finally {
                        isLoading = false
                    }
                },modifier = Modifier.padding(), colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ), border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Row(modifier = Modifier.padding(horizontal = 40.dp, vertical = 8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                        Image(painter = painterResource(id = R.drawable.google__1_), contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Login with Google", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
                Text(text = stringResource(id = R.string.copyright), modifier = Modifier.padding(bottom = 20.dp), color = MaterialTheme.colorScheme.surface)
            }
        }
    }
}