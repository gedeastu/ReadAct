package org.d3if3132.assesment02.readact.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.database.BookDb
import org.d3if3132.assesment02.readact.navigation.AddEditNavGraph
import org.d3if3132.assesment02.readact.navigation.DetailNavGraph
import org.d3if3132.assesment02.readact.ui.presentation.addedit_viewmodel.AddEditViewModel
import org.d3if3132.assesment02.readact.ui.presentation.main_viewmodel.MainViewModel
import org.d3if3132.assesment02.readact.util.SettingsDataStore
import org.d3if3132.assesment02.readact.util.ViewModelFactory
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current
    val db = BookDb.getInstance(context = context)
    val factory = ViewModelFactory(dao = db.dao)
    val viewModel : MainViewModel = viewModel(factory = factory)
    val controller : AddEditViewModel = viewModel(factory = factory)
    val datas by viewModel.datas.collectAsState(initial = emptyList())
    val orderBooks = datas.lastOrNull()
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(initial = true)

    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)){
                        Icon(imageVector = Icons.Default.Home, contentDescription = "home")
                        Text(text = stringResource(id = R.string.home))
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(painter = painterResource( id =
                            if (showList) R.drawable.baseline_grid_view_24
                            else R.drawable.baseline_view_list_24
                        ), contentDescription = if (showList) "Grid" else "List", tint = MaterialTheme.colorScheme.surface)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AddEditNavGraph.AddScreen.route)
            }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
            }
        }
    ){paddingValues ->
        if (orderBooks == null){
            Column(modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Icon(painter = painterResource(id = R.drawable.book), contentDescription = null, modifier = Modifier.size(100.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Empty Book List", color = MaterialTheme.colorScheme.primary)
            }   
        }else{
            if (showList){
                LazyColumn(modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 84.dp),
                    content = {
                        items(datas){book ->
                            Row(modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        AddEditNavGraph.EditScreen.withId(
                                            book.id
                                        )
                                    )
                                }
                                .border(
                                    1.5.dp,
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp), horizontalArrangement = Arrangement.Absolute.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                Column(Modifier.padding(start = 10.dp)){
                                    Text(text = book.title.uppercase(Locale.ROOT),maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
                                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)){
                                        Column {
                                            Text(text = book.genre, fontSize = 15.sp)
                                            Text(text = book.dateRelease, fontSize = 15.sp)
                                            Text(text = book.writer.lowercase(Locale.ROOT), fontSize = 15.sp)
                                        }
                                    }
                                }
                                Column {
                                    IconButton(onClick = {
                                        showDialog = true
                                    }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete", tint = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(onClick = {
                                        navController.navigate(AddEditNavGraph.EditScreen.withId(book.id))
                                    }) {
                                        Icon(imageVector = Icons.Default.Edit, contentDescription = "delete", tint = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(onClick = {
                                        navController.navigate(DetailNavGraph.DetailScreen.withId(book.id))
                                    }) {
                                        Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "detail", tint = MaterialTheme.colorScheme.primary)
                                    }
                                }
                                if (showDialog){
                                    DisplayAlertDialog(onConfirmation = {
                                        showDialog = false
                                        controller.delete(book.id)
                                        navController.popBackStack()
                                    }, onDismissRequest = { showDialog=false })
                                }
                            }
                        }
                    })
            }else{
                LazyVerticalStaggeredGrid(modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize(), columns = StaggeredGridCells.Fixed(2),verticalItemSpacing = 8.dp, horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(8.dp,8.dp,8.dp, 84.dp)) {
                    items(datas){ book ->
                        Column(modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    AddEditNavGraph.EditScreen.withId(
                                        book.id
                                    )
                                )
                            }
                            .border(
                                1.5.dp,
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center){
                            Column(Modifier.padding(start = 10.dp)){
                                Text(text = book.title.uppercase(Locale.ROOT),maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
                                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)){
                                    Column {
                                        Text(text = book.genre, fontSize = 15.sp)
                                        Text(text = book.dateRelease, fontSize = 15.sp)
                                        Text(text = book.writer.lowercase(Locale.ROOT), fontSize = 15.sp)
                                    }
                                }
                            }
                            Row{
                                IconButton(onClick = {
                                    showDialog = true
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = {
                                    navController.navigate(AddEditNavGraph.EditScreen.withId(book.id))
                                }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "delete", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = {
                                    navController.navigate(DetailNavGraph.DetailScreen.withId(book.id))
                                }) {
                                    Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "detail", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                            if (showDialog){
                                DisplayAlertDialog(onConfirmation = {
                                    showDialog = false
                                    controller.delete(book.id)
                                    navController.popBackStack()
                                }, onDismissRequest = { showDialog=false })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayAlertDialog(modifier: Modifier = Modifier, onConfirmation: ()->Unit, onDismissRequest:()->Unit) {
    AlertDialog(modifier = modifier,text = { Text(
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
