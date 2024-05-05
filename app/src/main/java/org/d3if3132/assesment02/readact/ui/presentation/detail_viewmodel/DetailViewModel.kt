package org.d3if3132.assesment02.readact.ui.presentation.detail_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3132.assesment02.readact.database.BookDao
import org.d3if3132.assesment02.readact.model.Book

class DetailViewModel(private val dao: BookDao): ViewModel(){
    fun insert(title:String, desc:String, date:Int,){
        val book = Book(title = title, desc = desc, date = date)
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(book = book)
        }
    }
}