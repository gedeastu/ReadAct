package org.d3if3132.assesment02.readact.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.database.BookDb
import org.d3if3132.assesment02.readact.ui.presentation.addedit_viewmodel.AddEditViewModel
import org.d3if3132.assesment02.readact.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController,id:Long? = null) {
    val context = LocalContext.current
    val db = BookDb.getInstance(context = context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: AddEditViewModel = viewModel(factory = factory)

    var title by remember {
        mutableStateOf("")
    }
    var desc by remember {
        mutableStateOf("")
    }
    val genreOptions = listOf(
        stringResource(id = R.string.action),
        stringResource(id = R.string.scifi),
        stringResource(id = R.string.horror),
        stringResource(id = R.string.drama),
        stringResource(id = R.string.romance)
    )
    var genre by rememberSaveable {
        mutableStateOf(genreOptions[0])
    }
    var writer by remember {
        mutableStateOf("")
    }
    val dateReleaseOptions = listOf(
        "2000 - 2010",
        "2011 - 2020",
        "2021",
        "2022",
        "2023",
        "2024",
        "Etc."
    )
    var dateRelease by remember {
        mutableStateOf(dateReleaseOptions[0])
    }

    var showDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit, block = {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBook(id) ?: return@LaunchedEffect
        title = data.title
        desc = data.desc
        genre = data.genre
        writer = data.writer
        dateRelease = data.dateRelease
        //selectedImageUri = data.cover.toUri()
    })
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detail Book")
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
                    if (id != null){
                        DeleteAction {
                            showDialog = true
                        }
                        DisplayAlertDialog(openDialog = showDialog, onDismissRequest = { showDialog=false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ){paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Column(modifier = Modifier
                .border(2.dp, color = MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.5f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                Text(text = title.uppercase(), fontWeight = FontWeight.ExtraBold, fontSize = 40.sp, color = MaterialTheme.colorScheme.primary)
                Text(text = genre, fontSize = 25.sp, color = MaterialTheme.colorScheme.primary)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically){
                    Text(text = writer, color = MaterialTheme.colorScheme.primary)
                    Text(text = dateRelease, color = MaterialTheme.colorScheme.primary)
                }
                Text(text = desc, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
@Composable
fun DeleteAction(delete: ()->Unit){
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.lainnya), tint = MaterialTheme.colorScheme.surface)
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(MaterialTheme.colorScheme.surface).shadow(1.dp)) {
        DropdownMenuItem(text = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)){
                Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.hapus), tint = MaterialTheme.colorScheme.primary)
                Text(text = stringResource(id = R.string.hapus), color = MaterialTheme.colorScheme.primary)
            }
                                },
            onClick = {
                expanded=false
                delete()
            },
        )
    }
}

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: ()->Unit,
    onConfirmation: ()->Unit
) {
    if(openDialog){
        AlertDialog(text = { Text(
            text = stringResource(id = R.string.pesan_hapus)) },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = { TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(id = R.string.tombol_hapus))
            } },
            dismissButton = { TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(id = R.string.tombol_batal))
            } }
        )
    }
}