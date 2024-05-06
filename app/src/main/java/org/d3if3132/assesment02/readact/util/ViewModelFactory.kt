package org.d3if3132.assesment02.readact.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3132.assesment02.readact.database.BookDao
import org.d3if3132.assesment02.readact.ui.presentation.addedit_viewmodel.AddEditViewModel
import org.d3if3132.assesment02.readact.ui.presentation.main_viewmodel.MainViewModel

class ViewModelFactory(private val dao: BookDao):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T:ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(AddEditViewModel::class.java)) {
            return AddEditViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}