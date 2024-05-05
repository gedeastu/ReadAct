package org.d3if3132.assesment02.readact.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3132.assesment02.readact.database.BookDao
import org.d3if3132.assesment02.readact.ui.presentation.detail_viewmodel.DetailViewModel
import org.d3if3132.assesment02.readact.ui.presentation.main_viewmodel.MainViewModel

class ViewModelFactory(private val dao: BookDao):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T:ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}