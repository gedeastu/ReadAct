package org.d3if3132.assesment02.readact.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.database.BookDb
import org.d3if3132.assesment02.readact.ui.presentation.addedit_viewmodel.AddEditViewModel
import org.d3if3132.assesment02.readact.util.ViewModelFactory

const val KEY_ID_BOOK = "idBook"
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(navController: NavHostController,id:Long? = null) {
    val context = LocalContext.current
    val db = BookDb.getInstance(context = context)
    val factory = ViewModelFactory(db.dao)
    val viewModel:AddEditViewModel = viewModel(factory = factory)

    var title by remember {
        mutableStateOf("")
    }
    var desc by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit, block = {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBook(id) ?: return@LaunchedEffect
        title = data.title
        desc = data.desc
        //selectedImageUri = data.cover.toUri()
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        if (id == null){
                            Text(text = stringResource(id = R.string.add_book))
                        }else{
                            Text(text = stringResource(id = R.string.edit_book))
                        }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    IconButton(content = {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back), tint = MaterialTheme.colorScheme.surface)
                    }, onClick = {
                        navController.popBackStack()
                    })
                },
                actions = {
                    IconButton(onClick = {
                        if (title == "" || desc == ""){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(title = title, desc = desc)
                        }else{
                            viewModel.update(id = id, title = title, desc = desc)
                        }
                        navController.popBackStack()
                    }, content = {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save", tint = MaterialTheme.colorScheme.surface)
                    })
                }
            )
        }
    ){paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedTextField(value = title, onValueChange = { title=it })
            OutlinedTextField(value = desc, onValueChange = { desc = it })
        }
    }
}