package org.d3if3132.assesment02.readact.ui.presentation.main_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3132.assesment02.readact.database.BookDao
import org.d3if3132.assesment02.readact.model.Book

class MainViewModel(dao: BookDao): ViewModel(){
    val datas: StateFlow<List<Book>> = dao.getBook().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}