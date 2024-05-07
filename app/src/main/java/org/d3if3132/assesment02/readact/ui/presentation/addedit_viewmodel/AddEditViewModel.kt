package org.d3if3132.assesment02.readact.ui.presentation.addedit_viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3if3132.assesment02.readact.database.BookDao
import org.d3if3132.assesment02.readact.model.Book

class AddEditViewModel(private val dao: BookDao): ViewModel(){
    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri : StateFlow<Uri?> = _selectedImageUri

    fun selectImage(uri: Uri){
        _selectedImageUri.value = uri
    }
    fun insert(title:String, desc:String, date:Int, cover: String){
        val book = Book(title = title, desc = desc, date = date, cover = cover)
        viewModelScope.launch(Dispatchers.IO){
            dao.insert(book = book)
        }
    }

    fun update(id:Long,title:String,desc:String,date:Int, cover: String){
        val book = Book(id = id, title = title, desc = desc, date = date, cover = cover)
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