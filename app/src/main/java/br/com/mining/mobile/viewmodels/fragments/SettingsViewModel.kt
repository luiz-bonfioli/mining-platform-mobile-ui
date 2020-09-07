package br.com.mining.mobile.viewmodels.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingsViewModel : ViewModel() {


    //region ~~~~ Factory ~~~~
    class Factory(
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel() as T
    }
    //endregion
}