package br.com.mining.mobile.viewmodels.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EquipmentPresentationViewModel : ViewModel() {

    //region ~~~~ Factory ~~~~
    class Factory(
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = EquipmentPresentationViewModel() as T
    }
    //endregion
}