package org.d3if3132.assesment02.readact.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
            )
        }
    ){paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Text(text = title.uppercase())
            Text(text = genre)
        }
    }
}