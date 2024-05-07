package org.d3if3132.assesment02.readact.ui.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
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
import coil.compose.rememberAsyncImagePainter
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.database.BookDb
import org.d3if3132.assesment02.readact.ui.presentation.addedit_viewmodel.AddEditViewModel
import org.d3if3132.assesment02.readact.util.ViewModelFactory

const val KEY_ID_BOOK = "idBook"
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
    //    var selectedImageUri by remember {
    //        mutableStateOf<Uri?>(null)
    //    }
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { viewModel.selectImage(it) }
    }
    //    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
    //        contract = ActivityResultContracts.PickVisualMedia(),
    //        onResult = {uri ->
    //            selectedImageUri = uri
    //        }
    //    )

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
                    containerColor = MaterialTheme.colorScheme.tertiary,
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
                        if (title == "" || desc == "" || selectedImageUri == null){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(title = title, desc = desc, cover = selectedImageUri.toString(), date = 2)
                        }else{
                            viewModel.update(id = id, title = title, desc = desc, cover = selectedImageUri.toString(), date = 2)
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
            .padding(16.dp)){
            selectedImageUri?.let { uri ->
                Image(painter = rememberAsyncImagePainter(uri), contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp))
            }
            //            if (selectedImageUri == null){
            //                Box(modifier = Modifier
            //                    .padding(bottom = 5.dp)
            //                    .fillMaxWidth()
            //                    .height(100.dp)
            //                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center){
            //                    Text(text = "Fill your Cover book")
            //                }
            //            }else{
            //                AsyncImage(model = selectedImageUri, contentDescription = null, modifier = Modifier
            //                    .padding(bottom = 20.dp)
            //                    .fillMaxWidth()
            //                    .height(100.dp), contentScale = ContentScale.Crop)
            //            }
            Button(modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),onClick = {
                    launcher.launch("image/*")
                //                singlePhotoPickerLauncher.launch(
                //                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                //                )
            }) {
                Text(text = "Take cover on gallery")
            }

            OutlinedTextField(value = title, onValueChange = { title=it })
            OutlinedTextField(value = desc, onValueChange = { desc = it })
        }
    }
}