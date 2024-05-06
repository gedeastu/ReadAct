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

    fun update(id:Long,title:String,desc:String,date:Int){
        val book = Book(id = id,title = title, desc = desc, date = date)
        viewModelScope.launch(Dispatchers.IO){
            dao.update(book = book)
        }
    }

    fun delete(id:Long){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteById(id = id)
        }
    }

    suspend fun getBook(id: Long):Book?{
        return dao.getBookById(id)
    }
}