package org.d3if3132.assesment02.readact.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.model.UserData
import org.d3if3132.assesment02.readact.util.SettingsDataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: ()->Unit
) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val darkMode by dataStore.themeFlow.collectAsState(initial = false)
    val isDarkMode by SettingsDataStore(LocalContext.current).themeFlow.collectAsState(
        initial = false
    )
    Scaffold(topBar = {
        TopAppBar(title = { 
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)){
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "profile")
                Text(text = "Profile")   
            }
        },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.surface
            )
            )
    }){
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            if (userData?.profilePictureUrl != null){
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            if (userData?.userName != null){
                Text(text = userData.userName, textAlign = TextAlign.Center, fontSize = 36.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))
            }
            Button(onClick = onSignOut) {
                Text(text = "Sign Out")
            }

            Switch(
                modifier = Modifier.padding(10.dp),
                checked = darkMode,
                onCheckedChange = { CoroutineScope(Dispatchers.IO).launch {
                    dataStore.toggleDarkMode()
                }},
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary
                ),
                thumbContent = {
                    if (isDarkMode){
                        Icon(painter = painterResource(id = R.drawable.baseline_nightlight_round_24), contentDescription = stringResource(
                            id = R.string.night
                        ), modifier = Modifier.padding(3.dp), )
                    }else{
                        Icon(painter = painterResource(id = R.drawable.baseline_sunny_24), contentDescription = stringResource(
                            id = R.string.day
                        ), modifier = Modifier.padding(3.dp))
                    }
                }
            )
        }    
    }
    
}