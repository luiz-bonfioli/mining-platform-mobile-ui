package br.com.mining.mobile.viewmodels.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MessageViewModel : ViewModel() {


    //region ~~~~ Factory ~~~~
    class Factory(
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MessageViewModel() as T
    }
    //endregion
}