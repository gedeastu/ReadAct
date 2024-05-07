package org.d3if3132.assesment02.readact.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.database.BookDb
import org.d3if3132.assesment02.readact.model.Book
import org.d3if3132.assesment02.readact.navigation.AddEditNavGraph
import org.d3if3132.assesment02.readact.navigation.DetailNavGraph
import org.d3if3132.assesment02.readact.ui.presentation.main_viewmodel.MainViewModel
import org.d3if3132.assesment02.readact.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = BookDb.getInstance(context = context)
    val factory = ViewModelFactory(dao = db.dao)
    val viewModel : MainViewModel = viewModel(factory = factory)
    val datas by viewModel.datas.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AddEditNavGraph.AddScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
            }
        }
    ){paddingValues ->
        if (datas.isEmpty()){
            Column(modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Data Kosong")
            }   
        }else{
            LazyColumn(modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp),
                content = {
                    items(datas){
                       ListItem(book = it, onClick = {
                            navController.navigate(DetailNavGraph.DetailScreen.withId(it.id))
                       })
                    }
            })
        }
    }
}

@Composable
fun ListItem(book: Book, onClick:()->Unit){
    Row(modifier = Modifier.padding(10.dp).fillMaxWidth()
        .clickable { onClick() }
        .border(1.5.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))
        .padding(16.dp), horizontalArrangement = Arrangement.Absolute.SpaceAround, verticalAlignment = Alignment.CenterVertically){
        Image(painter = rememberAsyncImagePainter(model = book.cover), contentDescription = book.cover, contentScale = ContentScale.Crop, modifier = Modifier.size(80.dp))
        Column {
            Text(text = book.title,maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
            Text(text = book.desc, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Text(text = book.date.toString())
        }
    }
}