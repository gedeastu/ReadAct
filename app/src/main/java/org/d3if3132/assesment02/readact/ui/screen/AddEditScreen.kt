package org.d3if3132.assesment02.readact.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var isExpandedDateRelease by remember {
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
                            viewModel.insert(title = title, desc = desc, genre = genre, writer = writer, dateRelease = dateRelease)
                        }else{
                            viewModel.update(id = id, title = title, desc = desc, genre = genre, writer = writer, dateRelease = dateRelease)
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

            OutlinedTextField(value = title, onValueChange = { title=it }, placeholder = { Text(text = "Title Book",color = MaterialTheme.colorScheme.primary) },modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp)), colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledPlaceholderColor = MaterialTheme.colorScheme.primary
            ))
            OutlinedTextField(value = writer, onValueChange = { writer = it }, placeholder = { Text(text = "Writer Book",color = MaterialTheme.colorScheme.primary) },modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp)), colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledPlaceholderColor = MaterialTheme.colorScheme.primary
            ))
            OutlinedTextField(value = desc, onValueChange = { desc = it }, placeholder = { Text(text = "Description", color = MaterialTheme.colorScheme.primary) },modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(300.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp)),colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledPlaceholderColor = MaterialTheme.colorScheme.primary
            ))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {isExpanded = !isExpanded}) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.9f)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(5.dp)
                            ),
                        value = genre,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                        onValueChange = {
                            genre = it
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.primary,
                            disabledBorderColor = MaterialTheme.colorScheme.primary,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.primary
                        ),
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        }
                    )
                    ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {
                        isExpanded = false
                    }) {
                        genreOptions.forEach{ value ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = value, color = MaterialTheme.colorScheme.primary)
                                },
                                onClick = {
                                    genre = value
                                    isExpanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                ExposedDropdownMenuBox(expanded = isExpandedDateRelease, onExpandedChange = {isExpandedDateRelease = !isExpandedDateRelease}) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.9f)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(5.dp)
                            ),
                        value = dateRelease,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                        onValueChange = {
                            dateRelease = it
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.primary,
                            disabledBorderColor = MaterialTheme.colorScheme.primary,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.primary
                        ),
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedDateRelease)
                        }
                    )
                    ExposedDropdownMenu(expanded = isExpandedDateRelease, onDismissRequest = {
                        isExpandedDateRelease = false
                    }) {
                        dateReleaseOptions.forEach{ value ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = value, color = MaterialTheme.colorScheme.primary)
                                },
                                onClick = {
                                    dateRelease = value
                                    isExpandedDateRelease = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)){
                Button(onClick = {
                    title = ""
                    desc = ""
                    writer = ""
                    genre = genreOptions[0]
                    dateRelease = dateReleaseOptions[0]
                }) {
                    Text(text = "Reset")
                }
                Button(onClick = {
                    if (title == "" || desc == "" || writer == ""){
                        Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                        return@Button
                    }
                    if (id == null){
                        viewModel.insert(title = title, desc = desc, genre = genre, writer = writer, dateRelease = dateRelease)
                    }else{
                        viewModel.update(id = id, title = title, desc = desc, genre = genre, writer = writer, dateRelease = dateRelease)
                    }
                    navController.popBackStack()
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}