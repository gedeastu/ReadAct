package org.d3if3132.assesment02.readact.ui.presentation.topbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3132.assesment02.readact.navigation.BottomBarNavGraph

class TopBarViewModel : ViewModel() {
    private val _currentTitle = MutableLiveData(BottomBarNavGraph.Home.title)
    val currentTitle: LiveData<String> = _currentTitle

    fun updateTitle(title: String){
        _currentTitle.value = title
    }
}